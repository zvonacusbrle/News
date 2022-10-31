package android.tvz.hr.newz.network.model

import android.tvz.hr.newz.domain.Article
import android.tvz.hr.newz.domain.util.ResponseMapper

class ArticleNetworkMapper : ResponseMapper<ArticleResponse, Article> {
    override fun mapFromResponse(response: ArticleResponse): Article {
        return Article(
            title = response.title,
            content = response.content,
            publishedAt = response.publishedAt,
            urlToImage = response.urlToImage

        )
    }
}