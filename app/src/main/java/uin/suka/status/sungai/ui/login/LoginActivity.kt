package uin.suka.status.sungai.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uin.suka.status.sungai.R
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.data.Resource
import uin.suka.status.sungai.databinding.ActivityLoginBinding
import uin.suka.status.sungai.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("login", "0")
        lifecycleScope.launch {
            Log.d("login", "3")
            val factory: ViewModelFactory = ViewModelFactory.getInstance(this@LoginActivity)
            Log.d("login", "4")
            val loginViewModel: LoginViewModel by viewModels {
                factory
            }
            binding.apply {
                btnLogin.setOnClickListener {
                    if (edLoginEmail.error.isNullOrEmpty() && edLoginPassword.error.isNullOrEmpty()) {
                        val email = edLoginEmail.text.toString().trim()
                        val password = edLoginPassword.text.toString().trim()
                        Log.d("login", "1")
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
    }

    private fun login(loginViewModel: LoginViewModel, email: String, password: String) {
        Log.d("login", "2")
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("login", "10")
            loginViewModel.getUserLogin(email, password).collect {
                Log.d("login", "11")
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        runOnUiThread {
                            Toast.makeText(
                                this@LoginActivity,
                                "SUKSES",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is Resource.Error -> {
                        showLoading(false)
                        runOnUiThread {
                            Toast.makeText(
                                this@LoginActivity,
                                R.string.login_failed,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        runOnUiThread {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}