package android.tvz.hr.newz.pagination

import android.content.ContentValues.TAG
import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.network.model.ArticleResponse
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ArticlePaging @Inject constructor(
    private val newsService: NewsService
) : PagingSource<Int, ArticleResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleResponse>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        try {
            val currentPageList = params.key ?: 1
            val response = newsService.getTopHeadlinesArticles(currentPageList)
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