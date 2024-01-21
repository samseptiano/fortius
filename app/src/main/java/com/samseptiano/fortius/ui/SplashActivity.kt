package com.samseptiano.fortius.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.samseptiano.fortius.BuildConfig
import com.samseptiano.fortius.data.dataStore.UserPreferences
import com.samseptiano.fortius.databinding.ActivitySplashBinding
import com.samseptiano.fortius.ui.viewmodel.SplashViewModel
import com.samseptiano.base.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private lateinit var viewModel: SplashViewModel

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        init()
        checkLogin()
        binding.apply {
            tvAppVersion.text = "v${BuildConfig.VERSION_NAME}"
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
    }

    private fun checkLogin() {
        userPreferences.isLogin.asLiveData().observe(this) {
            lifecycleScope.launch {
                delay(1500)
                if (it == true) {
                    goToHome()
                } else {
                    goToLogin()
                }
            }
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    }


    private fun goToHome() {
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        finish()
    }

}