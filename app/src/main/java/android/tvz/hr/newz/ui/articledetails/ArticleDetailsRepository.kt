package android.tvz.hr.newz.ui.articledetails

import android.content.ContentValues.TAG
import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.network.model.ArticleListResponse
import android.tvz.hr.newz.network.model.ArticleResponse
import android.tvz.hr.newz.ui.viewmodel.DEFAULT_QUERY
import android.util.Log
import retrofit2.Response
import javax.inject.Inject


class ArticleDetailsRepository @Inject constructor(
    private val newsService: NewsService
) {

    suspend fun getArticleByTitle(title: String) : ArticleResponse {
        Log.d(TAG, "getArticleByTitle: $title")
        val articleList = newsService.getArticleDetails(query = title)
        Log.d(TAG, "getArticleByTitle: $articleList.")

        return articleList.body()!!.articleResponses.get(0)
    }
}


