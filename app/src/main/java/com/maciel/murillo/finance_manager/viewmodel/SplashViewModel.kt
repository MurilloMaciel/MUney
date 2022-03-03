package com.maciel.murillo.finance_manager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maciel.murillo.finance_manager.model.repository.Repository
import com.maciel.murillo.finance_manager.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _userLogged = MutableLiveData<Event<Boolean>>()
    val userLogged: LiveData<Event<Boolean>> = _userLogged

    fun checkIfUserIsLogged() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userLogged.postValue(Event(repository.isUserLogged()))
            } catch (error: Exception) {
                _userLogged.postValue(Event(false))
            }
        }
    }
}