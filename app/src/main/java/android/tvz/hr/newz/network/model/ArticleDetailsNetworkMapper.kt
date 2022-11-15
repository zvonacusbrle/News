package android.tvz.hr.newz.network.model

import android.tvz.hr.newz.domain.ArticleDetailsUI
import android.tvz.hr.newz.domain.util.ArticleDetailsResponseMapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

class ArticleDetailsNetworkMapper : ArticleDetailsResponseMapper<ArticleResponse, ArticleDetailsUI> {
    override fun mapFromResponse(response: ArticleResponse): ArticleDetailsUI {
        return ArticleDetailsUI(
            title = response.title,
            content = response.content,
            publishedAt = convertedDate(response.publishedAt),
            urlToImage = response.urlToImage
        )
    }

    private fun convertedDate(date: String): String {
        return LocalDateTime
            .parse(date, ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
            .format(ofPattern("dd.MM.yyyyy HH:mm"))
    }
}