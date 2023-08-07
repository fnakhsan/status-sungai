package uin.suka.status.sungai.ui.register

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uin.suka.status.sungai.R
import uin.suka.status.sungai.core.factory.ViewModelFactory
import uin.suka.status.sungai.data.Resource
import uin.suka.status.sungai.databinding.ActivityRegisterBinding
import uin.suka.status.sungai.ui.home.HomeActivity
import uin.suka.status.sungai.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("login", "3")
            val factory: ViewModelFactory = ViewModelFactory.getInstance(this@RegisterActivity)
            Log.d("login", "4")
            val registerViewModel: RegisterViewModel by viewModels {
                factory
            }
            binding.apply {
                btnRegister.setOnClickListener {
                    if (edRegisterUsername.error.isNullOrEmpty() && edRegisterPassword.error.isNullOrEmpty() && edRegisterFullname.error.isNullOrEmpty()) {
                        val username = edRegisterUsername.text.toString().trim()
                        val password = edRegisterPassword.text.toString().trim()
                        val fullName = edRegisterFullname.text.toString().trim()
                        val community = edRegisterCommunity.text.toString().trim()
                        Log.d("login", "1")
                        register(registerViewModel, username, password, fullName, community)
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            R.string.login_failed,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                btnGuestLogin.setOnClickListener {
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                tvLogin.setOnClickListener {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun register(
        registerViewModel: RegisterViewModel,
        username: String,
        password: String,
        fullName: String,
        community: String?
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("login", "10")
            registerViewModel.registerUser(username, password, fullName, community).collect {
                Log.d("login", "11")
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        runOnUiThread {
                            Toast.makeText(
                                this@RegisterActivity,
                                it.data.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            toLogin()
                        }
                    }

                    is Resource.Error -> {
                        showLoading(false)
                        runOnUiThread {
                            Toast.makeText(
                                this@RegisterActivity,
                                it.error.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun toLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        runOnUiThread {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}