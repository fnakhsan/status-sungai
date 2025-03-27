package com.statussungai.android.ui.register

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.statussungai.android.R
import com.statussungai.android.core.utils.TextIsNotBlankUtil.textIsNotBlankListener
import com.statussungai.android.core.utils.UserType
import com.statussungai.android.core.utils.UserType.Companion.getTypeByUserType
import com.statussungai.android.data.Resource
import com.statussungai.android.databinding.ActivityRegisterBinding
import com.statussungai.android.ui.ActivityHelper.setupActivity
import com.statussungai.android.ui.components.errorToast
import com.statussungai.android.ui.home.HomeActivity
import com.statussungai.android.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModel<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setupActivity(activity = this, root = binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            binding.apply {
                textIsNotBlankListener(edRegisterUsername)
                textIsNotBlankListener(edRegisterFullName)
                btnRegister.setOnClickListener {
                    if (edRegisterUsername.error.isNullOrEmpty() && edRegisterPassword.error.isNullOrEmpty() && edRegisterFullName.error.isNullOrEmpty()) {
                        val username = edRegisterUsername.text.toString().trim()
                        val password = edRegisterPassword.text.toString().trim()
                        val fullName = edRegisterFullName.text.toString().trim()
                        val community = edRegisterCommunity.text.toString().trim()
                        Timber.d("onCreate: $username")
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
                    registerViewModel.setRole(getTypeByUserType(UserType.GUEST))
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
            registerViewModel.registerUser(username, password, fullName, community).collect {
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
                            errorToast(it.error)
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