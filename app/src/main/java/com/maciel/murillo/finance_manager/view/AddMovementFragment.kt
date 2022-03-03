package com.maciel.murillo.finance_manager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maciel.murillo.finance_manager.R
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maciel.murillo.finance_manager.databinding.FragmentAddMovementBinding
import com.maciel.murillo.finance_manager.extensions.getDateToString
import com.maciel.murillo.finance_manager.extensions.toDoubleSafe
import com.maciel.murillo.finance_manager.extensions.toStringValue
import com.maciel.murillo.finance_manager.model.entity.FinancialMovement
import com.maciel.murillo.finance_manager.model.entity.MovementType
import com.maciel.murillo.finance_manager.utils.DateHelper
import com.maciel.murillo.finance_manager.utils.EventObserver
import com.maciel.murillo.finance_manager.viewmodel.AddMovementViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddMovementFragment : Fragment() {

    private val addMovementViewModel by viewModels<AddMovementViewModel>()

    private val navController by lazy { findNavController() }

    private var _binding: FragmentAddMovementBinding? = null
    private val binding get() = _binding!!

    private val arguments: AddMovementFragmentArgs by navArgs()
    private val movementType: MovementType by lazy { arguments.movementType }

    private var movementDate = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddMovementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpListeners()
        setThemeByMovementType()
    }

    private fun setUpObservers() {
        addMovementViewModel.saveSuccessfull.observe(viewLifecycleOwner, EventObserver {
            navController.popBackStack()
        })

        addMovementViewModel.formError.observe(viewLifecycleOwner, EventObserver { error ->
            Toast.makeText(context, error.resource, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setUpListeners() {
        binding.fabDone.setOnClickListener {
            val value = binding.etValue.text.toString()
            addMovementViewModel.onClickDone(
                FinancialMovement(
                    value = value.toDoubleSafe(),
                    type = movementType.toStringValue(),
                    description = binding.etDescription.text.toString(),
                    category = binding.etCategory.text.toString(),
                    date = binding.tvDate.text.toString(),
                )
            )
        }

        binding.tvDate.setOnClickListener {
            DateSelectorDialog.show(childFragmentManager, movementDate) { year, month, day ->
                binding.tvDate.text = movementDate.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }.getDateToString()
            }
        }
    }

    private fun setThemeByMovementType() {
        val color = if (movementType == MovementType.INCOME) {
            R.color.colorPrimaryIncome
        } else {
            R.color.colorPrimaryExpense
        }

        binding.clExpense.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
        binding.fabDone.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), color)
        binding.fabDone.background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_done_white)
    }
}