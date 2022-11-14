package android.tvz.hr.newz.domain.util

interface ArticleDetailsResponseMapper <ArticleResponse, ArticleDetailsUI> {
    fun mapFromResponse(response: ArticleResponse) : ArticleDetailsUI
}