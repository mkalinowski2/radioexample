package pl.fewbits.radioexample.di

import okhttp3.Cache
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.fewbits.radioexample.lib.network.config.ApiBaseUrlProvider
import pl.fewbits.radioexample.lib.network.config.ApiCacheProvider

private const val CACHE_MAX_SIZE: Long = 20 * 1024 * 1024
private const val baseUrl = "https://s3-us-west-1.amazonaws.com"

val appModule = module {
    single { ApiCacheProvider(Cache(androidContext().cacheDir, CACHE_MAX_SIZE)) }
    single { ApiBaseUrlProvider(baseUrl.toHttpUrl()) }
}
