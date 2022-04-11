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
import com.maciel.murillo.finance_manager.databinding.FragmentSignupBinding
import com.maciel.murillo.muney.utils.EventObserver
import com.maciel.murillo.muney.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private val signupViewModel by viewModels<SignupViewModel>()

    private val navController by lazy { findNavController() }

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
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
        binding.btSignup.setOnClickListener {
            signupViewModel.onClickSignup(
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }

        binding.tvLogin.setOnClickListener {
            navController.navigate(SignupFragmentDirections.signupToLoginFrag())
        }
    }

    private fun setUpObservers() {
        signupViewModel.signupError.observe(viewLifecycleOwner, EventObserver { authError ->
            Snackbar.make(requireView(), authError.resource, Snackbar.LENGTH_SHORT)
                .setAction(R.string.understood, null)
                .show()
        })

        signupViewModel.signupSuccessfull.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(SignupFragmentDirections.signupToFinancesFrag())
        })
    }
}