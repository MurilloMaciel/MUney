package com.maciel.murillo.finance_manager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.maciel.murillo.finance_manager.extensions.isEmailValid
import com.maciel.murillo.finance_manager.model.AuthError
import com.maciel.murillo.finance_manager.model.repository.Repository
import com.maciel.murillo.finance_manager.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        coroutineContext.job.cancel()
        when (throwable) {
            is FirebaseAuthInvalidCredentialsException -> postLoginError(AuthError.WRONG_USER_INFO)
            is FirebaseAuthInvalidUserException -> postLoginError(AuthError.USER_NOT_EXISTS)
            else -> postLoginError(AuthError.GENERIC)
        }
    }

    private var job: Job? = null

    private val _loginError = MutableLiveData<Event<AuthError>>()
    val loginError: LiveData<Event<AuthError>> = _loginError

    private val _loginSuccessfull = MutableLiveData<Event<Unit>>()
    val loginSuccessfull: LiveData<Event<Unit>> = _loginSuccessfull

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun isFormValid(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> postLoginError(AuthError.EMPTY_EMAIL)
            password.isEmpty() -> postLoginError(AuthError.EMPTY_PASSWORD)
            email.isEmailValid().not() -> postLoginError(AuthError.EMAIL_INVALID)
            else -> true
        }
    }

    private fun postLoginError(authError: AuthError): Boolean {
        _loginError.postValue(Event(authError))
        return false
    }

    private fun login(email: String, password: String) {
        job = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.login(email, password)
            _loginSuccessfull.postValue(Event(Unit))
        }
    }

    fun onClickLogin(email: String, password: String) {
        if (isFormValid(email, password)) {
            login(email, password)
        }
    }
}