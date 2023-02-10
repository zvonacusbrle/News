package android.tvz.hr.newz.ui.toparticlesfragment


import android.content.ContentValues.TAG
import android.os.Bundle
import android.tvz.hr.newz.ALL_ARTICLES
import android.tvz.hr.newz.R
import android.tvz.hr.newz.databinding.FragmentTopArticlesBinding
import android.tvz.hr.newz.domain.ArticleUI
import android.tvz.hr.newz.ui.StateUI
import android.tvz.hr.newz.ui.adapter.ArticleAdapter
import android.tvz.hr.newz.ui.adapter.ArticleLoadStateAdapter
import android.tvz.hr.newz.ui.viewmodel.SharedViewModel
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



@AndroidEntryPoint
class TopArticlesFragment : Fragment() {
    private var _binding: FragmentTopArticlesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by viewModels()
    private var navController: NavController? = null
    private lateinit var adapter: ArticleAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopArticlesBinding.inflate(inflater, container, false)
        adapter = ArticleAdapter(
            onArticleClicked = { title -> startArticleDetailFragment(title) },
            onFavoriteClicked = { 1 }
        )


        viewModel.setArticleGroup(ALL_ARTICLES)

        binding.recyclerViewTop.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewTop.adapter =
            adapter.withLoadStateHeaderAndFooter(
                header = ArticleLoadStateAdapter { adapter.retry() },
                footer = ArticleLoadStateAdapter { adapter.retry() }
            )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateUI.collectLatest { state ->
                    when (state) {
                        is StateUI.Success -> {
                            state.articles.collectLatest { articles ->
                                binding.progressBar.visibility = GONE
                                adapter.submitData(articles)
                            }
                        }
                        StateUI.Loading ->
                            binding.progressBar.visibility = VISIBLE
                    }
                }
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topArticlesSearchBar.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    binding.recyclerViewTop.scrollToPosition(0)
                    if (newText != null) {
                        lifecycleScope.launch {
                            viewModel.searchNews(newText)
                        }
                    }
                    return true
                }
            })
        }
    }

    private fun startArticleDetailFragment(title: String) {
        val action =
            TopArticlesFragmentDirections.actionTopArticlesFragmentToArticleDetailsFragment(title)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible =
            true

        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.toolbar).isVisible =
            true
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible =
            false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

const val ARTICLE_TAG_FRAGMENT_KEY = "tagKey"
