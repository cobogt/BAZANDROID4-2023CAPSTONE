package com.jgt.core.storage.remote.interceptors

import com.jgt.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class ApiKeyInterceptor: Interceptor {
    private val apiKey = BuildConfig.API_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return chain.proceed(
            request.newBuilder()
                .url(
                    request
                        .url
                        .newBuilder()
                        .addQueryParameter("api_key", apiKey)
                        .build()
                )
                .build()
        )
    }
}
