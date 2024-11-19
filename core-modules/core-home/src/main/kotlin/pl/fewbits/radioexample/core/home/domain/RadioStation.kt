package pl.fewbits.radioexample.core.home.domain

data class RadioStation(
    val id: String,
    val description: String,
    val name: String,
    val imageUrl: String,
    val streamUrl: String,
    val reliability: Int,
    val popularity: Double?,
    val tags: List<String>
)
