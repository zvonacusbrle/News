package android.tvz.hr.newz.pagination

import android.tvz.hr.newz.TOP_ARTICLES
import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.network.model.ArticleListResponse
import android.tvz.hr.newz.network.model.ArticleResponse
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject

class ArticlePaging @Inject constructor(
    private val newsService: NewsService,
    private val query: String
) : PagingSource<Int, ArticleResponse>() {
    lateinit var response: Response<ArticleListResponse>
    override fun getRefreshKey(state: PagingState<Int, ArticleResponse>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        try {

            val currentPageList = params.key ?: 1
            response = newsService.getTopHeadlinesArticles(currentPageList, query)
           //     checkArticleGroup(newsService,articlesGroup,currentPageList)

            val responseList = mutableListOf<ArticleResponse>()
            val data = response.body()?.articleResponses ?: emptyList()

            responseList.addAll(data)

            val prevKey = if (currentPageList == 1) null else currentPageList - 1

            return LoadResult.Page(
                responseList,
                prevKey,
                currentPageList.plus(1)
            )

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }

    }


}