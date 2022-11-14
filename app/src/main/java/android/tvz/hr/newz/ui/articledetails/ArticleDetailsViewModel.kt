package android.tvz.hr.newz.ui.articledetails

import android.tvz.hr.newz.network.model.ArticleListResponse
import android.tvz.hr.newz.network.model.ArticleResponse
import android.tvz.hr.newz.network.model.Source
import android.tvz.hr.newz.ui.viewmodel.DEFAULT_QUERY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val articleDetailsRepository: ArticleDetailsRepository
) : ViewModel() {

    private val currentQuery = MutableStateFlow(QUERY)


    init {
        viewModelScope.launch {
           val articleDetails = articleDetailsRepository.getArticleByTitle(currentQuery.value)
        }


    }

    fun updateCurrentQuery(title: String) {
        currentQuery.value = title
    }
}

val QUERY =
    "Country star Dolly Parton gets \$100m award from Amazon founder Jeff Bezos"