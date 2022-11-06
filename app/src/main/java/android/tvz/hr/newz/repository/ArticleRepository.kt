package android.tvz.hr.newz.repository


import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.network.model.ArticleNetworkMapper
import android.tvz.hr.newz.pagination.ArticlePaging
import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val newsService: NewsService,
) {
    fun getSearchResult(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 1,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArticlePaging(newsService, query)}
        ).flow

}
