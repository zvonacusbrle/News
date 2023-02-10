package android.tvz.hr.newz.ui.articledetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val articleDetailsRepository: ArticleDetailsRepository
) : ViewModel() {

    private val currentQuery = MutableStateFlow(QUERY)
    private val _stateUI = MutableStateFlow<ArticleDetailsStateUI>(ArticleDetailsStateUI.Loading)
    val stateUI  = _stateUI.asStateFlow()

    fun updateCurrentQuery(title: String) {
        currentQuery.value = title
        getArticle(currentQuery.value)
    }

    private fun getArticle(value: String) = viewModelScope.launch {
        val articleDetails = articleDetailsRepository.getArticleByTitle(value)
        _stateUI.value = ArticleDetailsStateUI.Success(articleDetails)
    }
}

val QUERY =
    ""