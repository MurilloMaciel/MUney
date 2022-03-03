package com.maciel.murillo.finance_manager.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.maciel.murillo.finance_manager.R
import com.maciel.murillo.finance_manager.utils.EventObserver
import com.maciel.murillo.finance_manager.view.MainActivity.Companion.DESTINATION_FINANCES
import com.maciel.murillo.finance_manager.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val SPLASH_TIME = 2000L

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed(this::checkIfUserIsLogged, SPLASH_TIME)
    }

    private fun checkIfUserIsLogged() {
        splashViewModel.checkIfUserIsLogged()

        splashViewModel.userLogged.observe(this, EventObserver { userIsLogged ->
            if (userIsLogged) {
                MainActivity.start(this, DESTINATION_FINANCES)
            } else {
                IntroPagerActivity.start(this)
            }
        })
    }
}