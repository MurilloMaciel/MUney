package com.maciel.murillo.finance_manager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maciel.murillo.finance_manager.R
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.finance_manager.compose_test.FabTest
import com.maciel.murillo.finance_manager.databinding.FragmentLoginBinding
import com.maciel.murillo.finance_manager.utils.EventObserver
import com.maciel.murillo.finance_manager.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()

    private val navController by lazy { findNavController() }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false).also {
            it.composeViewLogin.setContent {
                MaterialTheme {
                    FabTest(
                        text = "login"
                    ) {
                        Toast.makeText(requireContext(), "login", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.btLogin.setOnClickListener {
            loginViewModel.onClickLogin(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }

        binding.tvSignup.setOnClickListener {
            navController.navigate(LoginFragmentDirections.loginToSignupFrag())
        }
    }

    private fun setUpObservers() {
        loginViewModel.loginError.observe(viewLifecycleOwner, EventObserver { authError ->
            Toast.makeText(context, authError.resource, Toast.LENGTH_SHORT).show()
        })

        loginViewModel.loginSuccessfull.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(LoginFragmentDirections.loginToFinancesFrag())
        })
    }
}