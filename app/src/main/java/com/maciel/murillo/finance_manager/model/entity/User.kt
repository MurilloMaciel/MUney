package com.maciel.murillo.finance_manager.model.entity

import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName
import com.maciel.murillo.finance_manager.model.service.DbConstants.FIREBASE_USERS_TOTAL_EXPENSES
import com.maciel.murillo.finance_manager.model.service.DbConstants.FIREBASE_USERS_TOTAL_INCOMES

@Keep
data class User(
    var email: String = "",

    @get:PropertyName("nome")
    @set:PropertyName("nome")
    var name: String = "",

    @Exclude
    var password: String = "",

    @get:PropertyName(FIREBASE_USERS_TOTAL_INCOMES)
    @set:PropertyName(FIREBASE_USERS_TOTAL_INCOMES)
    var totalIncome: Double = 0.0,

    @get:PropertyName(FIREBASE_USERS_TOTAL_EXPENSES)
    @set:PropertyName(FIREBASE_USERS_TOTAL_EXPENSES)
    var totalExpense: Double = 0.0,
)