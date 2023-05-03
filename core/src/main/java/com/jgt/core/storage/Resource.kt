package com.jgt.core.storage

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
import java.lang.Exception

sealed class Resource<out T> {
    data class Success<T>(
        val data: T? ): Resource<T>()

    data class Cache<T>(
        val data: T?, val exception: Exception, val message: String? = ""
    ): Resource<T>()

    data class Error<T>(
        val exception: Exception, val message: String? = ""): Resource<T>()

    data class Loading<T>(
        val data: Any? = null): Resource<T>()
}
