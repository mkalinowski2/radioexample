package pl.fewbits.radioexample.lib.network

import okhttp3.logging.HttpLoggingInterceptor
import pl.fewbits.radioexample.lib.network.config.ApiBaseUrlProvider
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiRetrofitProvider(
    private val baseUrlProvider: ApiBaseUrlProvider,
    private val okHttpClientFactory: ApiOkHttpClientFactory,
    private val moshiProvider: ApiMoshiProvider,
    private val enableLogging: Boolean = false,
    private val interceptors: List<HttpLoggingInterceptor> = emptyList()
) {

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrlProvider.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshiProvider.moshi))
            .client(okHttpClientFactory.create(createFinalInterceptors()))
            .build()
    }

    private fun createFinalInterceptors() = interceptors +
        listOfNotNull(
            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
                .takeIf { enableLogging }
        )

}
