package android.tvz.hr.newz.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.tvz.hr.newz.R
import android.tvz.hr.newz.databinding.FragmentFirstScreenBinding
import android.tvz.hr.newz.databinding.FragmentSecondScreenBinding


class SecondScreenFragment : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}