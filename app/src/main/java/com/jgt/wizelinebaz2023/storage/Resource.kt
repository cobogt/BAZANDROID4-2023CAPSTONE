package com.jgt.wizelinebaz2023.storage

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
import java.lang.Exception

sealed class Resource<out T> {
    data class Success<T>(
        val data: T?, val message: String? = ""): Resource<T>()

    data class Error<T>(
        val exception: Exception, val message: String? = ""): Resource<T>()

    data class Loading<T>(
        val data: Any? = null): Resource<T>()

    companion object {
        fun <T> success(
            data: T): Resource<T> = Success( data )

        fun <T> error(
            exception: Exception, message: String? = ""
        ): Resource<T> = Error( exception, message )

        fun <T> loading(
            data: Any? = null): Resource<T> = Loading( data )
    }
}
