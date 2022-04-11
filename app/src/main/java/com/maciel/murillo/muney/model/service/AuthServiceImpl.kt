package com.maciel.murillo.muney.model.service

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.maciel.murillo.muney.extensions.safe
import com.maciel.murillo.muney.extensions.toBase64
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthService {

    override suspend fun getCurrentUserId(): String = auth.currentUser?.email.safe().toBase64()

    override suspend fun isUserLogged() = auth.currentUser != null

    override suspend fun logout() = auth.signOut()

    override suspend fun signup(name: String, email: String, password: String): FirebaseUser? {
        try {
            val result: AuthResult = auth.createUserWithEmailAndPassword(email, password).await()
            return result.user
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun login(email: String, password: String): FirebaseUser? {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            return result.user
        } catch (e: Exception) {
            throw e
        }
    }
}