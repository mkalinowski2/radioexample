package pl.fewbits.radioexample.core.home

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreHomeModule = module {
    singleOf(::HomeNetwork)
    singleOf(::GetHomeUseCase)
}
