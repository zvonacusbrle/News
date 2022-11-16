package android.tvz.hr.newz.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.tvz.hr.newz.R
import android.tvz.hr.newz.databinding.FragmentFirstScreenBinding
import android.tvz.hr.newz.databinding.FragmentViewPagerBinding
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView


class FirstScreenFragment : Fragment() {

    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.firstFragmentButton.setOnClickListener{
            viewPager?.currentItem = 1
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible =
            false

        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.toolbar).isVisible =
            false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}