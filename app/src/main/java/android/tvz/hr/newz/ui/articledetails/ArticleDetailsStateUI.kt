package android.tvz.hr.newz.ui.articledetails

import android.tvz.hr.newz.domain.ArticleDetailsUI

sealed class ArticleDetailsStateUI {
    data class Success(val article: ArticleDetailsUI) : ArticleDetailsStateUI()
    object Loading : ArticleDetailsStateUI()
}