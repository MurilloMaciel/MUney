package com.maciel.murillo.finance_manager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.navigation.findNavController
import com.maciel.murillo.finance_manager.R
import com.maciel.murillo.finance_manager.compose_test.FabTest
import com.maciel.murillo.finance_manager.databinding.ActivityMainBinding
import com.maciel.murillo.finance_manager.extensions.safe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        handleStartDestination()
    }

    private fun handleStartDestination() {
        binding.fcvContainer.findNavController().graph =
            binding
                .fcvContainer
                .findNavController()
                .navInflater.inflate(R.navigation.nav_graph)
                .apply {
                    startDestination = when (intent?.getStringExtra(EXTRA_DESTINATION).safe()) {
                        DESTINATION_LOGIN -> R.id.frag_login
                        DESTINATION_SIGNUP -> R.id.frag_signup
                        else -> R.id.frag_finances
                    }
                }
    }

    companion object {
        private const val EXTRA_DESTINATION = "EXTRA_DESTINATION"
        const val DESTINATION_LOGIN = "DESTINATION_LOGIN"
        const val DESTINATION_FINANCES = "DESTINATION_FINANCES"
        const val DESTINATION_SIGNUP = "DESTINATION_SIGNUP"

        fun start(activity: AppCompatActivity, destination: String) {
            val intent = Intent(activity, MainActivity::class.java).apply {
                putExtra(EXTRA_DESTINATION, destination)
            }
            activity.startActivity(intent)
            activity.finish()
        }
    }
}