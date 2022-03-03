package com.maciel.murillo.finance_manager.view.finances

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.finance_manager.databinding.ViewFinanceBinding
import com.maciel.murillo.finance_manager.model.entity.FinancialMovement

class FinancesAdapter(
    private var financialMovements: List<FinancialMovement>
) : RecyclerView.Adapter<FinancesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancesViewHolder {
        val binding = ViewFinanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinancesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinancesViewHolder, position: Int) {
        holder.bind(financialMovements[position])
    }

    override fun getItemCount() = financialMovements.size

    fun update(financialMovements: List<FinancialMovement>) {
        this.financialMovements = financialMovements
        notifyDataSetChanged()
    }
}