package pl.fewbits.radioexample.lib.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.fewbits.radioexample.lib.network.config.ApiCacheProvider
import java.util.concurrent.TimeUnit

private const val TIMEOUT_SECONDS = 10L

class ApiOkHttpClientFactory(
    private val cacheProvider: ApiCacheProvider,
) {

    fun create(interceptors: List<HttpLoggingInterceptor>): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .let { builder -> interceptors.forEach { builder.addInterceptor(it) }.let { builder } }
        .apply { cacheProvider.cache?.let { cache(it) } }
        .build()
}
