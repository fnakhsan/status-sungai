package com.statussungai.android.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.statussungai.android.R
import com.statussungai.android.databinding.ActivityHomeBinding
import com.statussungai.android.ui.ActivityHelper.setupActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    //    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setupActivity(activity = this, root = binding.root)
//        supportFragmentManager.beginTransaction().apply {
//            setReorderingAllowed(true)
//            add(binding.navHost.id, HomeFragment())
//            commit()
//        }
//        val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
//        navController = navHostFragment.navController
//        setupWithNavController(binding.bottomNav, navController)


//        lifecycleScope.launch(Dispatchers.IO) {
//            mainViewModel.getToken().collectLatest {
//                runOnUiThread {
//                    showLoading(false)
//                    binding.apply {
//                        if (!it.isNullOrBlank()) {
//
//                        }
//                    }
////                    if (it.isNullOrBlank()) toLogin() else toHome(it)
//
//
//                }
//            }
//        }


    }
}