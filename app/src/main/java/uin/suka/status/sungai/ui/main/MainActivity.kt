package uin.suka.status.sungai.ui.main

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.core.utils.Const.EXTRA_TOKEN
import uin.suka.status.sungai.databinding.ActivityMainBinding
import uin.suka.status.sungai.ui.home.HomeActivity
import uin.suka.status.sungai.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch(Dispatchers.IO) {
            val factory: ViewModelFactory = ViewModelFactory.getInstance(this@MainActivity)
            val mainViewModel: MainViewModel by viewModels {
                factory
            }
            mainViewModel.getToken().collectLatest {
                showLoading(true)
                if (it.isNullOrBlank()) toLogin() else toHome(it)
            }
        }
    }

    private fun toLogin() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        showLoading(false)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }

    private fun toHome(token: String) {
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        intent.putExtra(EXTRA_TOKEN, token)
        showLoading(false)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        runOnUiThread {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}