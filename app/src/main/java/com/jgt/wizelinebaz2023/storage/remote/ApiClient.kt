package com.jgt.wizelinebaz2023.storage.remote

import com.jgt.wizelinebaz2023.storage.remote.interceptors.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
object ApiClient {
    private const val BASE_URL = "https://api.themoviedb.org/"

    private val retrofitClient = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor( ApiKeyInterceptor() )
        .build()

    private val retrofitBuilder = Retrofit
        .Builder()
        .baseUrl( BASE_URL )
        .addConverterFactory( GsonConverterFactory.create() )
        .client( retrofitClient )
        .build()

    fun <T>createService( serviceInterface: Class<T> ) = retrofitBuilder.create( serviceInterface )
}