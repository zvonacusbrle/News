package android.tvz.hr.newz.ui.adapter

import android.content.ContentValues.TAG
import android.tvz.hr.newz.R
import android.tvz.hr.newz.databinding.ArticleRowBinding
import android.tvz.hr.newz.domain.ArticleUI
import android.tvz.hr.newz.network.model.ArticleResponse
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.withContext

class ArticleViewHolder(
    private val binding: ArticleRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        article: ArticleUI,
        onArticleClicked: (title: String) -> Unit
    ) {
        with(binding) {
            rcArticleTitle.text = article.title

            binding.rvFavoriteButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)

            binding.rcArticleTitle.setOnClickListener {
                onArticleClicked(article.title.toString())
                Log.d(TAG, "onBind: ${article.title}")
            }
            Glide.with(itemView)
                .load(article.urlToImage)
                .centerCrop()
                .into(rvImageCover)
        }
    }
}