package android.tvz.hr.newz.repository


import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.pagination.ArticlePaging
import android.tvz.hr.newz.state.SortOrderState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val newsService: NewsService,
) {
    fun getSearchResult(query: String, sortState: SortOrderState, articleGroup: String) =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArticlePaging(newsService, query,sortState,articleGroup)}
        ).flow









}
