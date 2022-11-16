package android.tvz.hr.newz.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.tvz.hr.newz.R
import android.tvz.hr.newz.databinding.FragmentSecondScreenBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class SecondScreenFragment : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)


        binding.secondFragmentButton.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_topArticlesFragment)
            boardingFinished()
        }

        return binding.root
    }

    private fun boardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences(ONBOARDING, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(FINISHED, true)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible =
            false
    }
}

const val ONBOARDING = "onBoarding"
const val FINISHED = "Finished"