package com.maciel.murillo.finance_manager.extensions

import java.util.*

fun Calendar.getDateToString(): String {
    val year = this.get(Calendar.YEAR).toString().fillWithDefaultText(4, "0")
    val month = (this.get(Calendar.MONTH)+1).toString().fillWithDefaultText(2, "0")
    val day = this.get(Calendar.DAY_OF_MONTH).toString().fillWithDefaultText(2, "0")
    return "$year/$month/$day"
}