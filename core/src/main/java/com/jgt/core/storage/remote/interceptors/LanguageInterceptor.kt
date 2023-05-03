package com.jgt.core.storage.remote.interceptors

import com.jgt.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 26/04/23.
 * * * * * * * * * * **/
class LanguageInterceptor: Interceptor {
    private val locale = BuildConfig.LOCALE

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return chain.proceed(
            request.newBuilder()
                .url(
                    request
                        .url
                        .newBuilder()
                        .addQueryParameter("language", locale.ifEmpty { Locale.getDefault().language })
                        .build()
                )
                .build()
        )
    }
}
