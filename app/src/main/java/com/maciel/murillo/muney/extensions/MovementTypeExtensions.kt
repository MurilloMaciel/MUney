package com.maciel.murillo.muney.extensions

import com.maciel.murillo.muney.model.entity.MovementType

private const val INCOME = "R"
private const val EXPENSE = "D"

fun MovementType.toStringValue() = when (this) {
    MovementType.INCOME -> INCOME
    MovementType.EXPENSE -> EXPENSE
}

fun String.toMovementType() = when (this) {
    INCOME -> MovementType.INCOME
    EXPENSE -> MovementType.EXPENSE
    else -> null
}