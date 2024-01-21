package com.samseptiano.fortius.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.samseptiano.fortius.data.dataStore.UserPreferences
import com.samseptiano.fortius.databinding.ActivityLoginBinding
import com.samseptiano.fortius.ui.viewmodel.LoginViewModel
import com.samseptiano.fortius.utils.ConnectivityUtil
import com.samseptiano.base.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var viewModel: LoginViewModel
    @Inject
    lateinit var userPreferences: UserPreferences


    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        init()
        setupObserver()
        setupViews()
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }



    private fun setupViews() = with(binding) {
        btnLogin.setOnClickListener {
            if(ConnectivityUtil.isOnline(this@LoginActivity)) {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()

                if (viewModel.validateLogin(username, password)) {
                    showProgress()
                    lifecycleScope.launch {
                        viewModel.callLogin(
                            this@LoginActivity,
                            username,
                            password
                        )
                    }
                }
            }
        }
    }



    private fun setupObserver() {
        viewModel.errorResponseLiveData.observe(this) {
            Toast.makeText(this@LoginActivity, it.second, Toast.LENGTH_LONG).show()
        }
        viewModel.isLoginSuccess.observe(this) {
            if (it) {
                goToHome()
            }
            hideProgress()
            binding.btnLogin.visibility = View.VISIBLE
        }

    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    private fun goToHome() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()
    }
}