package android.tvz.hr.newz.domain

data class ArticleUI(
    val title: String?,
    val content: String?,
    val publishedAt: String?,
    val urlToImage: String?,
    var onArticleCheckedChange: Boolean = false
)