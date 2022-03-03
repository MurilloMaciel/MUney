package com.maciel.murillo.finance_manager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maciel.murillo.finance_manager.model.DataError
import com.maciel.murillo.finance_manager.model.entity.FinancialMovement
import com.maciel.murillo.finance_manager.model.entity.User
import com.maciel.murillo.finance_manager.model.repository.Repository
import com.maciel.murillo.finance_manager.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class FinancesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val logoutExceptionHandler = CoroutineExceptionHandler { coroutineContext, _ ->
        coroutineContext.job.cancel()
    }

    private val getDataExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        coroutineContext.job.cancel()
        _error.postValue(Event(DataError.GENERIC))
    }
    private var job: Job? = null

    var user: User? = null
    val movements = mutableListOf<FinancialMovement>()

    private val _logout = MutableLiveData<Event<Unit>>()
    val logout: LiveData<Event<Unit>> = _logout

    private val _error = MutableLiveData<Event<DataError>>()
    val error: LiveData<Event<DataError>> = _error

    private val _userReceived = MutableLiveData<Event<User>>()
    val userReceived: LiveData<Event<User>> = _userReceived

    private val _refreshMovements = MutableLiveData<Event<Unit>>()
    val refreshMovements: LiveData<Event<Unit>> = _refreshMovements

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private suspend fun getMovements(date: String) {
        val allMovements = repository.getAllMovements(date)
        movements.clear()
        movements.addAll(allMovements)
        _refreshMovements.postValue(Event(Unit))
    }

    private suspend fun getResume() {
        val user = repository.getCurrentUser()
        if (user == null) {
            _error.postValue(Event(DataError.GET_USER_ERROR))
        } else {
            this@FinancesViewModel.user = user
            _userReceived.postValue(Event(user))
        }
    }

    fun deleteMovement(movementPosition: Int) {
        job = viewModelScope.launch(Dispatchers.IO + getDataExceptionHandler) {
            repository.deleteMovement(movements[movementPosition])
            movements.removeAt(movementPosition)
            getResume()
            _refreshMovements.postValue(Event(Unit))
        }
    }

    fun onStartScreen(date: String) {
        job = viewModelScope.launch(Dispatchers.IO + getDataExceptionHandler) {
            getResume()
            getMovements(date)
        }
    }

    fun onChangeMonth(date: String) {
        job = viewModelScope.launch(Dispatchers.IO + getDataExceptionHandler) {
            getMovements(date)
        }
    }

    fun logout() {
        job = viewModelScope.launch(Dispatchers.IO + logoutExceptionHandler) {
            repository.logout()
            _logout.postValue(Event(Unit))
        }
    }
}