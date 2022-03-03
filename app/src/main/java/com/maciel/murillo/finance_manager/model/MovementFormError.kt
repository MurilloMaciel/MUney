package com.maciel.murillo.finance_manager.model

import com.maciel.murillo.finance_manager.R

enum class MovementFormError(val resource: Int) {

    DESCRIPTION(R.string.form_error_description),
    CATEGORY(R.string.form_error_category),
    DATE(R.string.form_error_date),
    VALUE(R.string.form_error_value),
    SAVE(R.string.form_error_save),
}
