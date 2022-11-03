package android.tvz.hr.newz.ui.adapter

import android.tvz.hr.newz.databinding.ArticleRowBinding
import android.tvz.hr.newz.network.model.ArticleResponse
import androidx.recyclerview.widget.RecyclerView

class ArticleViewHolder(
    private val binding: ArticleRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        article: ArticleResponse
    ) {
        with(binding) {
            rcArticleTitle.text = article.title
        }
    }
}