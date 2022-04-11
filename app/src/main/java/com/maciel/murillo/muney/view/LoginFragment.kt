package com.maciel.murillo.muney.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.maciel.murillo.finance_manager.R
import com.maciel.murillo.finance_manager.databinding.FragmentLoginBinding
import com.maciel.murillo.muney.utils.EventObserver
import com.maciel.murillo.muney.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

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
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
            Snackbar.make(requireView(), authError.resource, Snackbar.LENGTH_SHORT)
                .setAction(R.string.understood, null)
                .show()
        })

        loginViewModel.loginSuccessfull.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(LoginFragmentDirections.loginToFinancesFrag())
        })
    }
}