package com.maciel.murillo.finance_manager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.maciel.murillo.finance_manager.extensions.isEmailValid
import com.maciel.murillo.finance_manager.model.AuthError
import com.maciel.murillo.finance_manager.model.entity.User
import com.maciel.murillo.finance_manager.model.repository.Repository
import com.maciel.murillo.finance_manager.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        coroutineContext.job.cancel()
        when (throwable) {
            is FirebaseAuthWeakPasswordException -> postSignupError(AuthError.STRONGEST_PASSWORD)
            is FirebaseAuthInvalidCredentialsException -> postSignupError(AuthError.VALID_EMAIL)
            is FirebaseAuthUserCollisionException -> postSignupError(AuthError.ACCOUNT_ALREADY_EXISTS)
            else -> postSignupError(AuthError.GENERIC)
        }
    }

    private var job: Job? = null

    private val _signupError = MutableLiveData<Event<AuthError>>()
    val signupError: LiveData<Event<AuthError>> = _signupError

    private val _signupSuccessfull = MutableLiveData<Event<Unit>>()
    val signupSuccessfull: LiveData<Event<Unit>> = _signupSuccessfull

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun isFormValid(name: String, email: String, password: String): Boolean {
        return when {
            name.isEmpty() -> postSignupError(AuthError.EMPTY_NAME)
            email.isEmpty() -> postSignupError(AuthError.EMPTY_EMAIL)
            password.isEmpty() -> postSignupError(AuthError.EMPTY_PASSWORD)
            email.isEmailValid().not() -> postSignupError(AuthError.EMAIL_INVALID)
            else -> true
        }
    }

    private fun postSignupError(authError: AuthError): Boolean {
        _signupError.postValue(Event(authError))
        return false
    }

    private fun signup(name: String, email: String, password: String) {
        job = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.signup(name, email, password)
            val user = User(
                name = name,
                email = email,
                password = password
            )
            repository.saveUser(user)
            _signupSuccessfull.postValue(Event(Unit))
        }
    }

    fun onClickSignup(name: String, email: String, password: String) {
        if (isFormValid(name, email, password)) {
            signup(name, email, password)
        }
    }
}