package android.tvz.hr.newz.ui.toparticlesfragment

import android.content.ContentValues.TAG
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.tvz.hr.newz.R
import android.tvz.hr.newz.TOP_ARTICLES
import android.tvz.hr.newz.databinding.FragmentTopArticlesBinding
import android.tvz.hr.newz.ui.adapter.ArticleAdapter
import android.tvz.hr.newz.ui.adapter.ArticleLoadStateAdapter
import android.tvz.hr.newz.ui.viewmodel.DEFAULT_QUERY
import android.tvz.hr.newz.ui.viewmodel.SharedViewModel
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class TopArticlesFragment : Fragment() {
    // TODO: Rename and change types of parameters
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

        lifecycleScope.launch {
            viewModel.news.collectLatest { articles ->
                binding.recyclerViewTop.layoutManager = LinearLayoutManager(context)
                binding.recyclerViewTop.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = ArticleLoadStateAdapter { adapter.retry() },
                    footer = ArticleLoadStateAdapter { adapter.retry() }
                )
                adapter.submitData(articles)
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
                            viewModel.searchNews(newText)
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