package android.tvz.hr.newz.domain.util

interface ResponseMapper <ArticleResponse, Article> {

    fun mapFromResponse(response: ArticleResponse) : Article
}