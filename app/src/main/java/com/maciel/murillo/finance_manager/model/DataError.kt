package com.maciel.murillo.finance_manager.model

import com.maciel.murillo.finance_manager.R

enum class DataError(val resource: Int) {

    GET_USER_ERROR(R.string.data_error_get_user),
    GENERIC(R.string.data_error_generic),
}
