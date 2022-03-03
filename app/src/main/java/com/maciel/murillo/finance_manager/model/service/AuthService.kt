package com.maciel.murillo.finance_manager.model.service

import com.google.firebase.auth.FirebaseUser

interface AuthService {

    suspend fun isUserLogged(): Boolean

    suspend fun getCurrentUserId(): String

    suspend fun signup(name: String, email: String, password: String): FirebaseUser?

    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun logout()
}