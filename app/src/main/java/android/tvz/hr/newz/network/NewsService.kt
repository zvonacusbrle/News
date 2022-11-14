package android.tvz.hr.newz.network


import android.tvz.hr.newz.BuildConfig
import android.tvz.hr.newz.network.model.ArticleListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines?sources=bbc-news,techcrunch&apiKey=${BuildConfig.API_KEY}")
    suspend fun getTopHeadlinesArticles(
        @Query("page")  page:Int = 1,
        @Query("q") query: String? = null,
    ) : Response<ArticleListResponse>

    @GET("everything?apiKey=${BuildConfig.API_KEY}")
    suspend fun getAllArticles(
        @Query("page") page: Int = 1,
        @Query("q") query: String="tesla",
        @Query("sortBy") sortBy: String,

    ) : Response<ArticleListResponse>

    @GET("everything?apiKey=${BuildConfig.API_KEY}")
    suspend fun getArticleDetails(
        @Query("page") page: Int = 1,
        @Query("q") query: String = "tesla",
    ) : Response<ArticleListResponse>


}