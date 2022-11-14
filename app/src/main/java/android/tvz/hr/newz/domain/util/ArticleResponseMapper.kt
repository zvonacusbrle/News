package android.tvz.hr.newz.domain.util

interface ArticleResponseMapper <ArticleResponse, ArticleUI> {
    fun mapFromResponse(response: ArticleResponse) : ArticleUI
}