package com.maciel.murillo.finance_manager.view.finances

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.finance_manager.R
import com.maciel.murillo.finance_manager.databinding.ViewFinanceBinding
import com.maciel.murillo.finance_manager.extensions.toStringValue
import com.maciel.murillo.finance_manager.model.entity.FinancialMovement
import com.maciel.murillo.finance_manager.model.entity.MovementType

class FinancesViewHolder(
    private val binding: ViewFinanceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(financialMovement: FinancialMovement) = with(binding.root) {

        binding.tvTitle.text = financialMovement.description
        binding.tvCategory.text = financialMovement.category

        if (financialMovement.type == MovementType.INCOME.toStringValue()) {
            binding.tvValue.text = String.format(binding.root.context.getString(R.string.money_content), financialMovement.value.toString())
            binding.tvValue.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccentIncome))
        } else {
            binding.tvValue.text = String.format(binding.root.context.getString(R.string.negative_money_content), financialMovement.value.toString())
            binding.tvValue.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccentExpense))
        }
    }
}