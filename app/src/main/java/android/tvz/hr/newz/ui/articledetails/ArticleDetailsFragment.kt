package android.tvz.hr.newz.ui.articledetails


import android.content.ContentValues.TAG
import android.os.Bundle
import android.tvz.hr.newz.databinding.FragmentArticleDetailsBinding
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ArticleDetailsFragmentArgs by navArgs()
    private val viewModel : ArticleDetailsViewModel by viewModels()

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
        viewModel.updateCurrentQuery(args.title)

        lifecycleScope.launchWhenCreated {

        }


    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ArticleDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    @Deprecated("Deprecated in Java", ReplaceWith("menu.clear()"))
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}


const val ARTICLE_TAG_FRAGMENT_KEY = "tagKey"