package com.maciel.murillo.finance_manager.model.repository

import com.maciel.murillo.finance_manager.model.entity.FinancialMovement
import com.maciel.murillo.finance_manager.model.entity.User
import com.maciel.murillo.finance_manager.model.service.AuthService
import com.maciel.murillo.finance_manager.model.service.DbService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dbService: DbService
) : Repository {

    override suspend fun isUserLogged() = authService.isUserLogged()

    override suspend fun getCurrentUserId() = authService.getCurrentUserId()

    override suspend fun signup(name: String, email: String, password: String) = authService.signup(name, email, password)

    override suspend fun login(email: String, password: String) = authService.login(email, password)

    override suspend fun logout() = authService.logout()

    override suspend fun saveUser(user: User) = dbService.saveUser(user)

    override suspend fun saveMovement(movement: FinancialMovement) = dbService.saveMovement(movement)

    override suspend fun getCurrentUser() = dbService.getCurrentUser()

    override suspend fun getAllMovements(date: String) = dbService.getAllMovements(date)

    override suspend fun deleteMovement(movement: FinancialMovement) = dbService.deleteMovement(movement)

    override suspend fun updateBalance(movement: FinancialMovement) = dbService.updateBalance(movement)

    override suspend fun getTotalExpenses() = dbService.getTotalExpenses()

    override suspend fun getTotalIncomes() = dbService.getTotalIncomes()
}