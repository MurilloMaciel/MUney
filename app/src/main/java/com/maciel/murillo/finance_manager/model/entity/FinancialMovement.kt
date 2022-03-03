package com.maciel.murillo.finance_manager.model.entity

data class FinancialMovement(
    val date: String = "",
    val category: String = "",
    val description: String = "",
    val type: String = "",
    var key: String? = null,
    val value: Double = 0.0,
)