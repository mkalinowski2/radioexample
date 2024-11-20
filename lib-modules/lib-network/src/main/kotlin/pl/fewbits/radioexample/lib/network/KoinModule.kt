package pl.fewbits.radioexample.lib.network

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val libNetworkModule = module {
    singleOf(::ApiMoshiProvider)
    singleOf(::ApiOkHttpClientFactory)
    single { ApiRetrofitProvider(get(), get(), get(), false, emptyList()) }
}
