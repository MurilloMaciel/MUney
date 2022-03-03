package com.maciel.murillo.finance_manager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maciel.murillo.finance_manager.model.AuthError
import com.maciel.murillo.finance_manager.model.MovementFormError
import com.maciel.murillo.finance_manager.model.entity.FinancialMovement
import com.maciel.murillo.finance_manager.model.repository.Repository
import com.maciel.murillo.finance_manager.model.service.DbService
import com.maciel.murillo.finance_manager.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMovementViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        _formError.postValue(Event(MovementFormError.SAVE))
    }

    private var job: Job? = null

    private val _formError = MutableLiveData<Event<MovementFormError>>()
    val formError: LiveData<Event<MovementFormError>> = _formError

    private val _saveSuccessfull = MutableLiveData<Event<Unit>>()
    val saveSuccessfull: LiveData<Event<Unit>> = _saveSuccessfull

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun validForm(movement: FinancialMovement): Boolean {
        return when {
            movement.value.toString().isEmpty() -> postError(MovementFormError.VALUE)
            movement.value == 0.0 -> postError(MovementFormError.VALUE)
            movement.description.isEmpty() -> postError(MovementFormError.DESCRIPTION)
            movement.category.isEmpty() -> postError(MovementFormError.CATEGORY)
            movement.date.isEmpty() -> postError(MovementFormError.DATE)
            else -> true
        }
    }

    private fun postError(error: MovementFormError): Boolean {
        _formError.postValue(Event(error))
        return false
    }

    private fun saveMovement(movement: FinancialMovement) {
        job = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.saveMovement(movement)
            repository.updateBalance(movement)
            _saveSuccessfull.postValue(Event(Unit))
        }
    }

    fun onClickDone(movement: FinancialMovement) {
        if (validForm(movement)) {
            saveMovement(movement)
        }
    }
}