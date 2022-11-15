package android.tvz.hr.newz.ui

import android.tvz.hr.newz.domain.ArticleUI
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed class StateUI() {
    data class Success(val articles: Flow<PagingData<ArticleUI>>) : StateUI()
    object Loading : StateUI()
}