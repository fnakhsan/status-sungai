package uin.suka.status.sungai.ui.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import uin.suka.status.sungai.R
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.core.utils.FragmentUtil
import uin.suka.status.sungai.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNav, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val factory =
            ViewModelFactory.getInstance(this)
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }
        homeViewModel.getFragmentData().observe(this) {
            when (it) {
                FragmentUtil.MAPS -> {
                    Log.d("menu", "onCreateOptionsMenu: masuk1")
                    menuInflater.inflate(R.menu.maps_menu, menu)
                }
                FragmentUtil.ADD -> menuInflater.inflate(R.menu.maps_menu, menu)
                FragmentUtil.PROFILE -> menuInflater.inflate(R.menu.maps_menu, menu)
                null -> {}
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.maps_filter -> {

                Log.d("menu", "onOptionsItemSelected: masuk1")
                navController.navigate(R.id.action_mapsFragment_to_mapsFilterFragment)
                Log.d("menu", "onOptionsItemSelected: masuk2")
//                item.actionView?.findNavController()?.navigate(R.id.action_mapsFragment_to_mapsFilterFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}