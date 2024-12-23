package pl.fewbits.radioexample.feature.home

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val featureHomeModule = module {
    viewModelOf(::HomeViewModelImpl) { bind<HomeViewModel>() }
}
