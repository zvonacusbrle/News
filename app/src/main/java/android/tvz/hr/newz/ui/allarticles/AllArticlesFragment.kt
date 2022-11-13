package android.tvz.hr.newz.ui.allarticles

import android.os.Bundle
import android.tvz.hr.newz.databinding.FragmentAllArticlesBinding
import android.tvz.hr.newz.ui.viewmodel.SharedViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllArticlesFragment : Fragment() {
    private var _binding: FragmentAllArticlesBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

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


        return binding.root
    }


}