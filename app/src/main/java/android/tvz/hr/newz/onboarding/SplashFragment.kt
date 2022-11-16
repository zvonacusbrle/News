package android.tvz.hr.newz.onboarding

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.tvz.hr.newz.R
import android.tvz.hr.newz.onboarding.screens.FINISHED
import android.tvz.hr.newz.onboarding.screens.ONBOARDING
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(checkOnBoardingFinished()){
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_topArticlesFragment)
            }, 3000)
        }else {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }, 3000)
        }






        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun checkOnBoardingFinished() : Boolean{
        val sharedPref = requireActivity().getSharedPreferences("ONBOARDING", Context.MODE_PRIVATE)
        return sharedPref.getBoolean(FINISHED, false)

    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible =
            false

        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.toolbar).isVisible =
            false
    }


}