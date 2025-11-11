package pl.fewbits.radioexample.core.home

import java.util.Date

class GetHomeUseCase(private val network: HomeNetwork, private val date: Date) {
    suspend fun getHome() = network.getHome()
}
