package android.tvz.hr.newz.domain

data class ArticleUI(
    val title: String?,
    val content: String?,
    val publishedAt: String?,
    val urlToImage: String?,
    val onArticleCheckedChange: Boolean = false
)