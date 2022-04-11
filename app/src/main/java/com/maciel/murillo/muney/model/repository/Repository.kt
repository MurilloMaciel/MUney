package com.maciel.murillo.muney.model.repository

import com.google.firebase.auth.FirebaseUser
import com.maciel.murillo.muney.model.entity.FinancialMovement
import com.maciel.murillo.muney.model.entity.User

interface Repository {

    suspend fun isUserLogged(): Boolean

    suspend fun getCurrentUserId(): String

    suspend fun signup(name: String, email: String, password: String): FirebaseUser?

    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun logout()

    suspend fun saveUser(user: User)

    suspend fun saveMovement(movement: FinancialMovement)

    suspend fun getCurrentUser(): User?

    suspend fun getAllMovements(date: String): List<FinancialMovement>

    suspend fun deleteMovement(movement: FinancialMovement)

    suspend fun updateBalance(movement: FinancialMovement)

    suspend fun getTotalExpenses(): Double

    suspend fun getTotalIncomes(): Double
}