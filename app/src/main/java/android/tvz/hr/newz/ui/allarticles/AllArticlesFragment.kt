package android.tvz.hr.newz.ui.allarticles

import android.os.Bundle
import android.tvz.hr.newz.R

import android.tvz.hr.newz.databinding.FragmentAllArticlesBinding
import android.tvz.hr.newz.ui.StateUI
import android.tvz.hr.newz.ui.adapter.ArticleAdapter
import android.tvz.hr.newz.ui.adapter.ArticleLoadStateAdapter
import android.tvz.hr.newz.ui.toparticlesfragment.TopArticlesFragmentDirections
import android.tvz.hr.newz.ui.viewmodel.ALL_ARTICLES
import android.tvz.hr.newz.ui.viewmodel.SharedViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
class AllArticlesFragment : Fragment() {
    private var _binding: FragmentAllArticlesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by viewModels()
    private var navController: NavController? = null
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllArticlesBinding.inflate(inflater, container, false)
        adapter = ArticleAdapter(
            onArticleClicked = { title -> startArticleDetailFragment(title) },
            onFavoriteClicked = { 1 }
        )

        viewModel.setArticleGroup(ALL_ARTICLES)

        binding.recyclerViewAll.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewAll.adapter =
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
                                binding.progressBarTopArticles.visibility = View.GONE
                                adapter.submitData(articles)
                            }
                        }
                        StateUI.Loading ->
                            binding.progressBarTopArticles.visibility = View.VISIBLE
                    }
                }
            }
        }

        return binding.root
    }

    private fun startArticleDetailFragment(title: String) {
        val action =
            AllArticlesFragmentDirections.actionAllArticlesFragmentToArticleDetailsFragment(title)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible =
            true
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible =
            false
    }


}