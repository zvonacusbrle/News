package android.tvz.hr.newz

import android.os.Bundle
import android.tvz.hr.newz.databinding.ActivityMainBinding
import android.tvz.hr.newz.state.SortOrderState
import android.tvz.hr.newz.ui.viewmodel.SharedViewModel
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SharedViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.topArticlesFragment,
                R.id.allArticlesFragment)
        )
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sortAsc -> {
                viewModel.setSortState(SortOrderState.Ascending)
            }
            R.id.menu_sortDesc -> {
                viewModel.setSortState(SortOrderState.Descening)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

const val TOP_ARTICLES = "topArticles"
const val ALL_ARTICLES = "allArticles"