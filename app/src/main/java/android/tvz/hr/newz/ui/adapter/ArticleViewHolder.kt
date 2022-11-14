package android.tvz.hr.newz.ui.adapter

import android.content.ContentValues.TAG
import android.tvz.hr.newz.databinding.ArticleRowBinding
import android.tvz.hr.newz.network.model.ArticleResponse
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class ArticleViewHolder(
    private val binding: ArticleRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        article: ArticleResponse,
        onArticleClicked: (title: String) -> Unit
    ) {
        with(binding) {
            rcArticleTitle.text = article.title

            binding.rcArticleTitle.setOnClickListener {
                onArticleClicked(article.title.toString())
                Log.d(TAG, "onBind: ${article.title}")
            }
        }
    }
}