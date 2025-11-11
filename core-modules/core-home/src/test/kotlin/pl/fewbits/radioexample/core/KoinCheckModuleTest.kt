package pl.fewbits.radioexample.core

import io.mockk.mockk
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import pl.fewbits.radioexample.core.home.GetHomeUseCase
import pl.fewbits.radioexample.core.home.coreHomeModule
import pl.fewbits.radioexample.lib.network.ApiRetrofitProvider

class KoinCheckModuleTest : KoinTest {

    @Test
    fun checkModules() {
        startKoin {
            modules(coreHomeModule, module {
                single { mockk<ApiRetrofitProvider>() }
            })
        }

        val useCase = get<LoginViewModel>()

        val scope = getKoin().createScope<LoginViewModel>()
        val useCase = scope.get<LoginViewModel>()
        val useCase2 = scope.get<GetHomeUseCase>()*/

        assert(useCase != null)
    }
}
