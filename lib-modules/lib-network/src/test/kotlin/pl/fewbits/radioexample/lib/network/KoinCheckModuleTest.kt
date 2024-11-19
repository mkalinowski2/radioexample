package pl.fewbits.radioexample.lib.network

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify
import pl.fewbits.radioexample.lib.network.config.ApiBaseUrlProvider
import pl.fewbits.radioexample.lib.network.config.ApiCacheProvider

@OptIn(KoinExperimentalAPI::class)
class KoinCheckModuleTest {

    @Test
    fun checkModules() {
        networkModule.verify(listOf(ApiCacheProvider::class, ApiBaseUrlProvider::class, Boolean::class, List::class))
    }
}
