package android.tvz.hr.newz.ui

import android.tvz.hr.newz.network.model.ArticleResponse
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed class StateUI() {
    data class Success(val articles: Flow<PagingData<ArticleResponse>>) : StateUI()
    object Loading : StateUI()

}