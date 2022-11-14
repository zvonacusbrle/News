package android.tvz.hr.newz.ui.articledetails

import android.content.ContentValues.TAG
import android.tvz.hr.newz.domain.ArticleDetailsUI
import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.network.model.ArticleDetailsNetworkMapper
import android.util.Log
import javax.inject.Inject


class ArticleDetailsRepository @Inject constructor(
    private val newsService: NewsService
) {
    val mapper = ArticleDetailsNetworkMapper()
    suspend fun getArticleByTitle(title: String) : ArticleDetailsUI {

        val articles = newsService.getArticleDetails(query = title)
        val articleUI = articles.body()?.articleResponses?.map { articleResponse ->
            mapper.mapFromResponse(articleResponse)
        } ?: emptyList()
        return articleUI[0]
    }
}


