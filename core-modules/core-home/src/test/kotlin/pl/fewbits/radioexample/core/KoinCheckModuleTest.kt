package pl.fewbits.radioexample.core

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify
import pl.fewbits.radioexample.core.home.coreHomeModule
import pl.fewbits.radioexample.lib.network.ApiRetrofitProvider

@OptIn(KoinExperimentalAPI::class)
class KoinCheckModuleTest {

    @Test
    fun checkModules() {
        coreHomeModule.verify(listOf(ApiRetrofitProvider::class))
    }
}
