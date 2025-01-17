package com.jgt.core.storage
/** * * * * * * * * *
 * Project KoreFrame
 * Created by Jacobo G Tamayo on 30/12/22.
* * * * * * * * * * **/

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface RepositoryStrategy {
    /**
     * Estrategia para leer información de la red y persistirla en local
     *
     * Tipo R: Tipo de dato del parámetro de entrada para llamar a la fuente remota (Remote/DTO)
     * Tipo L: Tipo de dato del almacenamiento persistente local (Entity)
     * Tipo M: Tipo de dato que se utiliza en la capa de dominio (Model)
     */
    data class FetchTransformAndStoreStrategy<R, L, M>(
        /** Llamada a la fuente de datos remota */
        val remoteSourceData: suspend () -> R,

        /** Fuente local observable */
        val localSourceData: suspend () -> Flow<L>,

        /** Transforma la respuesta de la llamada LOCAL de la capa DATA,
         *  devuelve Modelos de la capa DOMAIN */
        val localToModelTransform: suspend (L) -> M,

        /** Transforma la respuesta de la llamada REMOTA de la capa DATA, devuelve
         * Modelos de la  capa DOMINIO */
        val remoteToModelTransform: suspend (R) -> M,

        /** Almacena el resultado de la función de transformación (Opcional) */
        val storeModelTransformation: (suspend (M) -> Unit)? = null
    ): RepositoryStrategy {
        /**
         * Ejecuta la petición a la fuente remota para luego transformar la respuesta y si está
         * definida la función de almacenamiento se ejecuta con la respuesta transformada.
         *
         * Consume una fuente local observable, y en cada emisión de valores se usa la
         * función de transformación para mapear el resultado. Si está definida la función de
         * almacenamiento, guarda el resultado de la transformación invocandola.
         */
        fun consume(): Flow<M> =
            flow {
                try {
                    fetch()
                }catch (e: IOException) {
                    /* Este bloque permite trabajar offline */
                    Log.w("FetchTransformAndStoreStrategy", "IOException: $e")
                }catch (e: HttpException){
                    /* Este bloque permite trabajar sin servicio */
                    Log.w("FetchTransformAndStoreStrategy", "HttpException: $e")
                }

                emitAll(
                    localSourceData
                        .invoke()
                        .mapNotNull { localResult ->
                            localResult?.let {
                                localToModelTransform( localResult )
                            }
                        }
                )
            }

        /**
         * Envuelve el resultado del flujo consumido en un recurso
         */
        fun consumeAsResource(): Flow<Resource<M>> =
            flow {
                emit(Resource.Loading())

                var fetchException: Exception? = null

                try {
                    try { fetch() }
                    catch (e: IOException) { fetchException = e }
                    catch (e: HttpException) { fetchException = e }

                    emitAll(
                        localSourceData
                            .invoke()
                            .mapNotNull { localResult ->
                                localResult?.let {
                                    val localDataAsModel = localToModelTransform( localResult )

                                    if( fetchException == null )
                                        Resource.Success(localDataAsModel)
                                    else
                                        Resource.Cache(localDataAsModel, fetchException)
                                }
                            }
                    )
                }catch (e: Exception) {
                    emit(Resource.Error(e))
                }
            }

        /**
         * Reintenta la operación de consumo de fuente remota, transformación
         * de respuesta y almacenamiento del resultado transformado.
         *
         * Esta operación se puede ejecutar sin consumir la estrategia si solo se busca
         * hacer la petición, transformar la respuesta y persistir el resultado.
         */
        suspend fun fetch() = remoteSourceData.invoke().also {
            val remoteResponse = remoteSourceData.invoke()

            CoroutineScope( Dispatchers.IO ).launch {
                storeModelTransformation?.invoke(
                    remoteToModelTransform ( remoteResponse )
                )
            }
        }
    }

    /**
     * Estrategia para consumir información de la red y transformarla a un modelo local sin
     * persistir pero pudiendo reintentar la operación.
     */
    data class FetchAndTransformStrategy<R, M>(
        /** Llamada a la fuente de datos remota */
        val remoteSourceData:       suspend () -> R,

        /** Transforma la respuesta de la llamada REMOTA de la capa DATA, devuelve
         * Modelos de la  capa DOMINIO */
        val remoteToModelTransform: suspend (R) -> M,

        /** Almacena el resultado de la función de transformación (Opcional) */
        val storeModelTransformation:      (suspend (M) -> Unit)? = null,
    ): RepositoryStrategy {
        private var virtualStorage: MutableStateFlow<R?> = MutableStateFlow( null )
        fun consume(): Flow<M> = flow {
            try {
                fetch()
            }catch (e: IOException) {
                /* Este bloque permite trabajar offline */
                Log.w("FetchAndTransformStrategy", "IOException: $e")
            }catch (e: HttpException){
                /* Este bloque permite trabajar sin servicio */
                Log.w("FetchAndTransformStrategy", "HttpException: $e")
            }

            emitAll(
                virtualStorage.mapNotNull {
                    it?.let { remoteData ->
                        remoteToModelTransform.invoke( remoteData )
                    }
                }
            )
        }

        fun consumeAsResource(): Flow<Resource<M>> = flow {
            var fetchException: Exception? = null

            try {
                try { fetch() }
                catch (e: IOException) { fetchException = e }
                catch (e: HttpException) { fetchException = e }

                emitAll(
                    virtualStorage.mapNotNull {
                        it?.let { remoteData ->
                            val remoteDataAsModel = remoteToModelTransform.invoke( remoteData )

                            if( fetchException == null )
                                Resource.Success(remoteDataAsModel)
                            else
                                Resource.Cache(remoteDataAsModel, fetchException)
                        }
                    }
                )
            } catch ( e: Exception ) {
                emit(Resource.Error(e))
            }
        }

        suspend fun fetch() {
            val remoteData = remoteSourceData.invoke()
            virtualStorage.value = remoteData

            CoroutineScope( Dispatchers.IO ).launch{
                storeModelTransformation?.invoke(
                    remoteToModelTransform.invoke( remoteData )
                )
            }
        }
    }
}
