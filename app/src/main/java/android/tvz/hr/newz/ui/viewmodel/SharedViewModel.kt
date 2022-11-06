package android.tvz.hr.newz.ui.viewmodel

import android.content.ContentValues.TAG
import android.tvz.hr.newz.network.NewsService
import android.tvz.hr.newz.network.model.ArticleResponse
import android.tvz.hr.newz.pagination.ArticlePaging
import android.tvz.hr.newz.repository.ArticleRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import retrofit2.http.Query
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val newsService: NewsService,
    private val articleRepository: ArticleRepository
) : ViewModel(){

    private val _currentQuery = MutableStateFlow<String>(DEFAULT_QUERY)
    val currentQuery = _currentQuery.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val news = _currentQuery.flatMapLatest { query ->
        articleRepository.getSearchResult(query).cachedIn(viewModelScope)
    }

    fun searchNews(query: String){
        _currentQuery.value = query
    }


}

private const val DEFAULT_QUERY = "tesla"