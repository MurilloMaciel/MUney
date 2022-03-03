package com.maciel.murillo.finance_manager.view.finances

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.finance_manager.R
import com.maciel.murillo.finance_manager.databinding.FragmentFinancesBinding
import com.maciel.murillo.finance_manager.model.entity.MovementType
import com.maciel.murillo.finance_manager.model.entity.User
import com.maciel.murillo.finance_manager.utils.EventObserver
import com.maciel.murillo.finance_manager.viewmodel.FinancesViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import javax.inject.Inject

@AndroidEntryPoint
class FinancesFragment : Fragment() {

    private val financesViewModel by viewModels<FinancesViewModel>()

    private val navController by lazy { findNavController() }

    private var _binding: FragmentFinancesBinding? = null
    private val binding get() = _binding!!

    private var monthAndYearSelected = ""

    private lateinit var financesAdapter: FinancesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinancesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpCalendarView()
        setUpSwipe()
        setUpObservers()
        setUpListeners()

        financesAdapter = FinancesAdapter(financesViewModel.movements)
        binding.contentFinances.rvFinances.adapter = financesAdapter
    }

    override fun onStart() {
        super.onStart()

        financesViewModel.onStartScreen(monthAndYearSelected)
    }

    private fun setUpObservers() {
        financesViewModel.logout.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(FinancesFragmentDirections.financesToLoginFrag())
        })

        financesViewModel.error.observe(viewLifecycleOwner, EventObserver { error ->
            Toast.makeText(context, error.resource, Toast.LENGTH_SHORT).show()
        })

        financesViewModel.userReceived.observe(viewLifecycleOwner, EventObserver { user ->
            setUserValues(user)
        })

        financesViewModel.refreshMovements.observe(viewLifecycleOwner, EventObserver {
            financesAdapter.notifyDataSetChanged()
            binding.contentFinances.tvFinancesEmpty.visibility = if (financesViewModel.movements.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
    }

    private fun setUserValues(user: User) {
        val money = NumberFormat.getCurrencyInstance().format(user.totalIncome - user.totalExpense)
        binding.contentFinances.tvMoney.text = money.toString()
        binding.contentFinances.tvGreetings.text = String.format(resources.getString(R.string.greetings), user.name)
    }

    private fun setUpListeners() {
        binding.fabExpenses.setOnClickListener {
            navigateToAddMovement(MovementType.EXPENSE)
        }

        binding.fabIncomes.setOnClickListener {
            navigateToAddMovement(MovementType.INCOME)
        }

        binding.contentFinances.ivLogout.setOnClickListener {
            financesViewModel.logout()
        }

        binding.contentFinances.mcvCalendar.setOnMonthChangedListener { _, date ->
            date?.run {
                setMonthAndYearSelected(date)
                financesViewModel.onChangeMonth(monthAndYearSelected)
            }
        }
    }

    private fun navigateToAddMovement(movementType: MovementType) {
        navController.navigate(FinancesFragmentDirections.financesToAddMovementFrag(movementType))
    }

    private fun setUpCalendarView() {
        binding.contentFinances.mcvCalendar.setTitleMonths(resources.getStringArray(R.array.months))
        val today = binding.contentFinances.mcvCalendar.currentDate
        setMonthAndYearSelected(today)
    }

    private fun setMonthAndYearSelected(date: CalendarDay) {
        val selectedMonth = String.format("%02d", (date.month + 1))
        monthAndYearSelected = "$selectedMonth${date.year}"
    }

    private fun setUpSwipe() {
        val itemTouch = object : ItemTouchHelper.Callback() {

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val dragFlags = ItemTouchHelper.ACTION_STATE_IDLE
                val swipeFlasg = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags, swipeFlasg)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = deleteMovement(viewHolder.adapterPosition)
        }
        ItemTouchHelper(itemTouch).attachToRecyclerView(binding.contentFinances.rvFinances)
    }

    private fun deleteMovement(position: Int) {
        DeleteMovementDialog.show(
            manager = childFragmentManager,
            onClickDelete = { financesViewModel.deleteMovement(position) },
            onClickCancel = { financesAdapter.notifyDataSetChanged() }
        )
    }
}