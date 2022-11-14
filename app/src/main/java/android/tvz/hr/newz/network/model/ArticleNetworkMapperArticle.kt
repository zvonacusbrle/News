package android.tvz.hr.newz.network.model

import android.tvz.hr.newz.domain.ArticleUI
import android.tvz.hr.newz.domain.util.ArticleResponseMapper

class ArticleNetworkMapperArticle : ArticleResponseMapper<ArticleResponse, ArticleUI> {
    override fun mapFromResponse(response: ArticleResponse): ArticleUI {
        return ArticleUI(
            title = response.title,
            content = response.content,
            publishedAt = response.publishedAt,
            urlToImage = response.urlToImage
        )
    }
}