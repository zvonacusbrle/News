package android.tvz.hr.newz

import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.network.model.ArticleResponse
import android.tvz.hr.newz.pagination.ArticlePaging
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val newsService: NewsService
) : ViewModel(){

    fun getArticles(articlesGroup: String) : Flow<PagingData<ArticleResponse>> {
        return Pager(config = PagingConfig(pageSize = 1, maxSize = 5),
        pagingSourceFactory = {ArticlePaging(newsService, articlesGroup)}).flow.cachedIn(viewModelScope)
    }
}