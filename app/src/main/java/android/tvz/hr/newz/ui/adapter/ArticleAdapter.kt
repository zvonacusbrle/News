package android.tvz.hr.newz.ui.adapter

import android.tvz.hr.newz.databinding.ArticleRowBinding
import android.tvz.hr.newz.network.model.ArticleResponse
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

class ArticleAdapter(
    private val onArticleClicked: (title: String) -> Unit
) : PagingDataAdapter<ArticleResponse, ArticleViewHolder>(DiffCallback()) {


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem((position))?.let { holder.onBind(it, onArticleClicked) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ArticleViewHolder(binding)
    }

    companion object {
        private class DiffCallback : DiffUtil.ItemCallback<ArticleResponse>() {
            //2
            override fun areItemsTheSame(oldItem: ArticleResponse, newItem: ArticleResponse) =
                oldItem.title == newItem.title

            //3
            override fun areContentsTheSame(oldItem: ArticleResponse, newItem: ArticleResponse) =
                oldItem == newItem
        }
    }


}