package pl.fewbits.radioexample.feature.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.fewbits.radioexample.android.ui.viewmodel.BaseViewModel
import pl.fewbits.radioexample.core.home.GetHomeUseCase
import pl.fewbits.radioexample.feature.home.state.HomeState
import pl.fewbits.radioexample.feature.home.state.HomeStateItem

class HomeViewModelImpl(
    private val getHomeUseCase: GetHomeUseCase
) : HomeViewModel() {

    private val stateFlow = MutableStateFlow<HomeState>(HomeState.Loading)

    override fun getScreenStateFlow(): StateFlow<HomeState> = stateFlow.asStateFlow()

    override fun loadContent() {
        stateFlow.value = HomeState.Loading
        viewModelScope.launch {
            runCatching { getHomeUseCase.getHome() }
                .onSuccess { response -> stateFlow.value = HomeState.Content(response.radioStations.map { HomeStateItem(it) }) }
                .onFailure { stateFlow.value = HomeState.Error }
        }
    }
}

internal class HomeViewModelMock(screenState: HomeState) : HomeViewModel() {
    private val screenStateFlow = MutableStateFlow(screenState)
    override fun getScreenStateFlow(): StateFlow<HomeState> = screenStateFlow.asStateFlow()

    override fun loadContent() {}
}

abstract class HomeViewModel : BaseViewModel() {
    abstract fun getScreenStateFlow(): StateFlow<HomeState>
    abstract fun loadContent()
}
