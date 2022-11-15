package android.tvz.hr.newz.ui.adapter

import android.graphics.Color
import android.tvz.hr.newz.R
import android.tvz.hr.newz.databinding.ArticleRowBinding
import android.tvz.hr.newz.domain.ArticleUI
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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

            binding.rvArticle.setOnClickListener {
                onArticleClicked(article.title.toString())
            }

            if (article.onArticleCheckedChange) {
                binding.rvFavoriteButton.setImageResource(R.drawable.ic_baseline_bookmark_24)
                binding.rvFavoriteButton.background.isVisible
            } else {
                binding.rvFavoriteButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                binding.rvFavoriteButton.setBackgroundColor(Color.parseColor("#fbfbfb"));
            }
            Glide.with(itemView)
                .load(article.urlToImage)
                .centerCrop()
                .into(rvImageCover)
        }
    }
}