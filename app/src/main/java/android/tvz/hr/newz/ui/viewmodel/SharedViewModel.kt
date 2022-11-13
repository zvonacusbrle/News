@file:OptIn(ExperimentalCoroutinesApi::class)

package android.tvz.hr.newz.ui.viewmodel

import android.tvz.hr.newz.repository.ArticleRepository
import android.tvz.hr.newz.state.SortOrderState
import android.tvz.hr.newz.ui.StateUI
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SharedViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val currentQuery = MutableStateFlow(DEFAULT_QUERY)
    val sortState = MutableStateFlow<SortOrderState>(SortOrderState.Ascending)
    private val articleGroup = MutableStateFlow(ALL_ARTICLES)
    private val _stateUI = MutableStateFlow<StateUI>(StateUI.Loading)
    val stateUI  = _stateUI.asStateFlow()


    init {
        viewModelScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            val flow = currentQuery
                .debounce(1300)
                .filter {
                    it.trim().isNotEmpty()
                }
                .map { it.trim().lowercase(Locale.ROOT) }
                .distinctUntilChanged()
                .combine(sortState) { query, sort -> query to sort }
                .flatMapLatest { (query, sort) ->
                    articleRepository.getSearchResult(query, sort,articleGroup.value)
                }
            _stateUI.value = StateUI.Success(flow)
        }
    }





    fun searchNews(query: String) {
        if (query.isNotEmpty()) {
            currentQuery.value = query
        } else {
            currentQuery.value = DEFAULT_QUERY
        }

    }

    fun setSortState(sortOrderState: SortOrderState) {
           sortState.value = sortOrderState
    }

    fun setArticleGroup(group: String) {
        articleGroup.value = group
    }





}


const val DEFAULT_QUERY = "US"
const val TOP_ARTICLES = "topArticles"
const val ALL_ARTICLES = "allArticles"
