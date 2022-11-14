package android.tvz.hr.newz.network.model

import android.tvz.hr.newz.domain.ArticleDetailsUI
import android.tvz.hr.newz.domain.util.ArticleDetailsResponseMapper

class ArticleDetailsNetworkMapper : ArticleDetailsResponseMapper<ArticleResponse, ArticleDetailsUI> {
    override fun mapFromResponse(response: ArticleResponse): ArticleDetailsUI {
        return ArticleDetailsUI(
            title = response.title,
            content = response.content,
            publishedAt = response.publishedAt,
            urlToImage = response.urlToImage
        )
    }
}