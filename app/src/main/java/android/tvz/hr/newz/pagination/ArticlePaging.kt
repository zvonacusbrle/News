package android.tvz.hr.newz.pagination

import android.content.ContentValues.TAG
import android.tvz.hr.newz.TOP_ARTICLES
import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.network.model.ArticleListResponse
import android.tvz.hr.newz.network.model.ArticleResponse
import android.tvz.hr.newz.state.SortOrderState
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ArticlePaging @Inject constructor(
    private val newsService: NewsService,
    private val query: String,
    private val sortState: SortOrderState,
    private val articleGroup: String
) : PagingSource<Int, ArticleResponse>() {
    lateinit var response: Response<ArticleListResponse>
    override fun getRefreshKey(state: PagingState<Int, ArticleResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        try {
            val currentPageList = params.key ?: 1
            response = getResponse(currentPageList, newsService,query,sortState, articleGroup)
            Log.d(TAG, "load: $sortState $articleGroup" )
             //   newsService.getTopHeadlinesArticles(currentPageList, query)

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

    private suspend fun getResponse(
        currentPageList: Int,
        newsService: NewsService,
        query: String,
        sortState: SortOrderState,
        articleGroup: String
    ): Response<ArticleListResponse> {
       val response: Response<ArticleListResponse>
        response = if(articleGroup == TOP_ARTICLES){
            newsService.getTopHeadlinesArticles(currentPageList, query)
        } else{
            when(sortState){
                SortOrderState.Ascending ->  newsService.getAllArticles(currentPageList, query, ASCENDING)
                else -> newsService.getAllArticles(currentPageList, query, DESCENDING)
            }

        }
        return response

    }


}

private const val ASCENDING = "publishedAt"
private const val DESCENDING = "-publishedAt"