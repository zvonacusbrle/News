package android.tvz.hr.newz.ui.toparticlesfragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.tvz.hr.newz.ALL_ARTICLES
import androidx.fragment.app.Fragment
import android.tvz.hr.newz.databinding.FragmentTopArticlesBinding
import android.tvz.hr.newz.ui.StateUI
import android.tvz.hr.newz.ui.adapter.ArticleAdapter
import android.tvz.hr.newz.ui.adapter.ArticleLoadStateAdapter
import android.tvz.hr.newz.ui.viewmodel.SharedViewModel
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class TopArticlesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentTopArticlesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopArticlesBinding.inflate(inflater, container, false)
        val adapter = ArticleAdapter()
        viewModel.setArticleGroup(ALL_ARTICLES)



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
             viewModel.stateUI.collectLatest { state ->
                 when(state){
                     is StateUI.Success -> {
                           state.articles.collectLatest { articles ->
                               binding.progressBar.visibility = GONE
                               binding.recyclerViewTop.layoutManager = LinearLayoutManager(context)
                               binding.recyclerViewTop.adapter = adapter.withLoadStateHeaderAndFooter(
                                   header = ArticleLoadStateAdapter { adapter.retry() },
                                   footer = ArticleLoadStateAdapter { adapter.retry() }
                               )
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


        lifecycleScope.launch {
            viewModel.sortState.collect {
                Log.d(TAG, "onCreateViewSort: $it")
            }
        }

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
                            viewModel.sortState.update {
                                it
                            }
                        }

                    }
                    return true
                }
            })
        }


    }


    companion object {
        fun newInstance(param1: String, param2: String) =
            TopArticlesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}