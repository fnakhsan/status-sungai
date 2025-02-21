package com.statussungai.android.ui.login

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.statussungai.android.R
import com.statussungai.android.core.factory.ViewModelFactory
import com.statussungai.android.core.utils.TextIsNotBlankUtil.textIsNotBlankListener
import com.statussungai.android.data.Resource
import com.statussungai.android.databinding.ActivityLoginBinding
import com.statussungai.android.ui.ActivityHelper.setupActivity
import com.statussungai.android.ui.components.errorToast
import com.statussungai.android.ui.home.HomeActivity
import com.statussungai.android.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setupActivity(activity = this, root = binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@LoginActivity)
        val loginViewModel: LoginViewModel by viewModels {
            factory
        }

        binding.apply {
            textIsNotBlankListener(edLoginUsername)
            btnLogin.setOnClickListener {
                if (edLoginUsername.error.isNullOrEmpty() && edLoginPassword.error.isNullOrEmpty()) {
                    val email = edLoginUsername.text.toString().trim()
                    val password = edLoginPassword.text.toString().trim()
                    login(loginViewModel, email, password)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        R.string.login_failed,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            tvRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun login(loginViewModel: LoginViewModel, email: String, password: String) {
        loginViewModel.loginUser(email, password).observe(this@LoginActivity) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    toHome()
                }

                is Resource.Error -> {
                    showLoading(false)
                    runOnUiThread {
                        errorToast(it.error)
                    }
                }
            }
        }
    }

    private fun toHome() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}