package android.tvz.hr.newz.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleListResponse(
    @Json(name = "articles")
    val articleResponses: List<ArticleResponse>,
    @Json(name = "status")
    val status: String,
    @Json(name = "totalResults")
    val totalResults: Int
)