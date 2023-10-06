package likelion.project.giphy.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiphyApiResponse(
    val data: List<Giphy>,
)

@Serializable
data class Giphy(
    val title: String,
    val images: Images
)

@Serializable
data class Images(
    val original: Original
)

@Serializable
data class Original(
    val url: String,
)