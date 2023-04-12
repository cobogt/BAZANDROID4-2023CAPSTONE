package com.jgt.wizelinebaz2023.storage.remote.interceptors

import com.jgt.wizelinebaz2023.BuildConfig
import com.jgt.wizelinebaz2023.core.BaseApplication
import okhttp3.Interceptor
import okhttp3.Response

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("api_key", BuildConfig.API_KEY)
                .build()
        )
}