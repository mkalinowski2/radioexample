package pl.fewbits.radioexample.core.home

class GetHomeUseCase(private val network: HomeNetwork) {
    suspend fun getHome() = network.getHome()
}
