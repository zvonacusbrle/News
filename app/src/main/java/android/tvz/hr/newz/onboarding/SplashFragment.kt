package android.tvz.hr.newz.onboarding

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.tvz.hr.newz.R
import android.tvz.hr.newz.databinding.FragmentSplashBinding
import android.tvz.hr.newz.onboarding.screens.FINISHED
import android.tvz.hr.newz.onboarding.screens.ONBOARDING
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class SplashFragment : Fragment() {


    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        val backgroundImg = binding.njuzImageSplashScreen
        val sideAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_splash)
        backgroundImg.startAnimation(sideAnimation)

        if (checkOnBoardingFinished()) {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_topArticlesFragment)
            }, 3000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }, 3000)
        }
        return binding.root
    }

    private fun checkOnBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences(ONBOARDING, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(FINISHED, false)
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