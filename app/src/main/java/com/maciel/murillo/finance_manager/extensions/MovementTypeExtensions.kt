package com.maciel.murillo.finance_manager.extensions

import com.maciel.murillo.finance_manager.model.entity.MovementType

fun MovementType.toStringValue() = when (this) {
    MovementType.INCOME -> "R"
    MovementType.EXPENSE -> "D"
}

fun String.toMovementType() = when (this) {
    "R" -> MovementType.INCOME
    "D" -> MovementType.EXPENSE
    else -> null
}