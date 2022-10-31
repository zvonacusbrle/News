package android.tvz.hr.newz.repository

import android.tvz.hr.newz.domain.Article
import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.network.model.ArticleNetworkMapper
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val newsService: NewsService,
    private val mapper: ArticleNetworkMapper
) {
    suspend fun getTopArticles(): List<Article> {
        val topArticles = newsService.getTopHeadlinesArticles().articleResponses
            .map { article ->
                mapper.mapFromResponse(article)
            }
        return topArticles
    }
}