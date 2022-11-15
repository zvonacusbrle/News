package android.tvz.hr.newz.ui.articledetails


import android.os.Bundle
import android.tvz.hr.newz.R
import android.tvz.hr.newz.databinding.FragmentArticleDetailsBinding
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch



@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {
    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ArticleDetailsFragmentArgs by navArgs()
    private val viewModel: ArticleDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true);

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateCurrentQuery(args.title)
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateUI.collect { state ->
                    when (state) {
                        is ArticleDetailsStateUI.Success -> {
                            showArticleDetails(state)
                            binding.detailsProgressBar.visibility = View.GONE
                        }
                        ArticleDetailsStateUI.Loading ->
                            binding.detailsProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showArticleDetails(state: ArticleDetailsStateUI.Success) {
        binding.apply {
            articleDetailsTitle.text = state.article.title
            detailsContent.text = state.article.content
            detailsPublishedAt.text = state.article.publishedAt
            context?.let {
                Glide
                    .with(it)
                    .load(state.article.urlToImage)
                    .centerCrop()
                    .placeholder(R.drawable.ic_news)
                    .into(detailsImage)
            }
        }
    }


    @Deprecated("Deprecated in Java", ReplaceWith("menu.clear()"))
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}


const val ARTICLE_TAG_FRAGMENT_KEY = "tagKey"