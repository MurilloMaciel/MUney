package com.maciel.murillo.finance_manager.utils

import java.text.SimpleDateFormat

object DateHelper {

    fun getActualDate(): String {
        val currentTimeMillies = System.currentTimeMillis()
//        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(currentTimeMillies)
    }
}