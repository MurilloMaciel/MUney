package com.maciel.murillo.finance_manager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import com.maciel.murillo.finance_manager.R
import com.maciel.murillo.finance_manager.view.MainActivity.Companion.DESTINATION_LOGIN
import com.maciel.murillo.finance_manager.view.MainActivity.Companion.DESTINATION_SIGNUP

class IntroPagerActivity : IntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.view_slider_intro_1)
            .build()
        )
        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.view_slider_intro_2)
            .build()
        )
        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.view_slider_intro_3)
            .build()
        )
        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.view_slider_intro_4)
            .build()
        )
        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .canGoForward(false)
            .fragment(R.layout.view_slider_intro_signup)
            .build()
        )
    }

    private fun navigateToMain(destination: String) {
        MainActivity.start(this, destination)
    }

    fun onClickSignup(view: View) = navigateToMain(DESTINATION_SIGNUP)

    fun onClickLogin(view: View) = navigateToMain(DESTINATION_LOGIN)

    companion object {

        fun start(activity: AppCompatActivity) {
            val intent = Intent(activity, IntroPagerActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}