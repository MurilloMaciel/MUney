package com.maciel.murillo.finance_manager.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

class DateSelectorDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var calendar: Calendar? = null
    private lateinit var onDateSet: (Int, Int, Int) -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initCalendar()
        return DatePickerDialog(requireActivity(), this, getYear(), getMonth(), getDay())
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if (view.isShown) {
            onDateSet.invoke(year, monthOfYear, dayOfMonth)
        }
    }

    private fun initCalendar() {
        if (calendar == null) {
            this.calendar = Calendar.getInstance()
        }
    }

    private fun getDay(): Int {
        return calendar!!.get(Calendar.DAY_OF_MONTH)
    }

    private fun getMonth(): Int {
        return calendar!!.get(Calendar.MONTH)
    }

    private fun getYear(): Int {
        return calendar!!.get(Calendar.YEAR)
    }

    companion object {
        fun show(manager: FragmentManager, calendar: Calendar?, onDateSet: (Int, Int, Int) -> Unit) {
            val dialog = DateSelectorDialog()
            dialog.calendar = calendar
            dialog.onDateSet = onDateSet
            dialog.show(manager, DateSelectorDialog::class.java.name)
        }
    }
}