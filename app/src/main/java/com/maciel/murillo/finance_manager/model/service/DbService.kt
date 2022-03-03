package com.maciel.murillo.finance_manager.model.service

import com.maciel.murillo.finance_manager.model.entity.FinancialMovement
import com.maciel.murillo.finance_manager.model.entity.User

interface DbService {

    suspend fun saveUser(user: User)

    suspend fun saveMovement(movement: FinancialMovement)

    suspend fun getCurrentUser(): User?

    suspend fun getAllMovements(date: String): List<FinancialMovement>

    suspend fun deleteMovement(movement: FinancialMovement)

    suspend fun updateBalance(movement: FinancialMovement)

    suspend fun getTotalExpenses(): Double

    suspend fun getTotalIncomes(): Double
}