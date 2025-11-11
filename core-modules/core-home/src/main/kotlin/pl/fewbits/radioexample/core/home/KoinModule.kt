package pl.fewbits.radioexample.core.home

import org.koin.core.module.dsl.factoryOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import java.util.Date

val coreHomeModule = module {

    factoryOf(::HomeNetwork)
    factory {
        val state = Date()
        GetHomeUseCase(get { parametersOf(state) }, state)
    }
    /*
    scope<GetHomeUseCase> {
        scoped { Date() }
        scopedOf(::HomeNetwork)
        scopedOf(::GetHomeUseCase)
    }*/
}
