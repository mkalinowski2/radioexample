package pl.fewbits.radioexample.feature.radiodetails

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.fewbits.radioexample.core.home.domain.RadioStation

val featureRadioDetailsModule = module {
    viewModel<RadioDetailsViewModel> { (radioStation: RadioStation) -> RadioDetailsViewModelImpl(radioStation, get()) }
}
