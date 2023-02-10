package android.tvz.hr.newz.ui.adapter

import android.content.ContentValues.TAG
import android.tvz.hr.newz.R
import android.tvz.hr.newz.databinding.ArticleRowBinding
import android.tvz.hr.newz.domain.ArticleUI
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ArticleAdapter(
    private val onFavoriteClicked: (position: Int) -> Unit,
    private val onArticleClicked: (title: String) -> Unit
) : PagingDataAdapter<ArticleUI, ArticleAdapter.ArticleViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem((position))?.let { holder.onBind(it, onFavoriteClicked, onArticleClicked) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ArticleViewHolder(binding)
    }



        private class DiffCallback : DiffUtil.ItemCallback<ArticleUI>() {
            //2
            override fun areItemsTheSame(oldItem: ArticleUI, newItem: ArticleUI) =
                oldItem.title == newItem.title

            //3
            override fun areContentsTheSame(oldItem: ArticleUI, newItem: ArticleUI) =
                oldItem == newItem
        }

    fun checkFavoriteButton(position: Int, favorite: Boolean){
        Log.d(TAG, "checkFavoriteButton: $position $favorite")
        snapshot()[position]?.onArticleCheckedChange = favorite
        notifyItemChanged(position)
    }


 inner class ArticleViewHolder(
     private val binding: ArticleRowBinding
 ) : RecyclerView.ViewHolder(binding.root) {
     fun onBind(
         article: ArticleUI,
         onFavoriteClicked: (position: Int) -> Unit,
         onArticleClicked: (title: String) -> Unit
     ) {
         with(binding) {
             rcArticleTitle.text = article.title
             binding.rvFavoriteButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
             binding.rvArticle.setOnClickListener {
                 onArticleClicked(article.title.toString())
             }


             Glide.with(itemView)
                 .load(article.urlToImage)
                 .centerCrop()
                 .into(rvImageCover)

             binding.rvFavoriteButton.setOnClickListener {
                 article.onArticleCheckedChange  = !article.onArticleCheckedChange


              //   checkFavoriteButton(position = position, article.onArticleCheckedChange)


                 if (article.onArticleCheckedChange) {
                     binding.rvFavoriteButton.setImageResource(R.drawable.ic_baseline_bookmark_24)
                 } else {
                     binding.rvFavoriteButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                 }
             }
         }
     }
 }



}