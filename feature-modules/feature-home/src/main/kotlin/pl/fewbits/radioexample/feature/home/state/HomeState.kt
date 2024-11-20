package pl.fewbits.radioexample.feature.home.state

sealed class HomeState {

    data object Loading: HomeState()
    data object Error: HomeState()

    data class Content(val items: List<HomeStateItem>) : HomeState()



}

