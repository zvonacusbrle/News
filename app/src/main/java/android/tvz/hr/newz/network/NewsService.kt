package android.tvz.hr.newz.network


import android.tvz.hr.newz.BuildConfig
import android.tvz.hr.newz.network.model.ArticleListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines?sources=bbc-news&apiKey=${BuildConfig.API_KEY}")
    suspend fun getTopHeadlinesArticles(
        @Query("pageSize") pageSize: Int = 3,
        @Query("page")  page:Int = 1
    ) : ArticleListResponse
}