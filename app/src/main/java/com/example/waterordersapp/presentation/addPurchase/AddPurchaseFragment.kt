package com.example.waterordersapp.presentation.addPurchase

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.waterordersapp.R
import com.example.waterordersapp.databinding.FragmentAddPurchaseBinding
import com.example.waterordersapp.domain.model.Month
import com.example.waterordersapp.domain.model.PaymentStatus
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddPurchaseFragment : Fragment(R.layout.fragment_add_purchase) {
    private val viewModel: AddPurchaseViewModel by viewModels()
    private var _binding: FragmentAddPurchaseBinding? = null
    private val binding: FragmentAddPurchaseBinding
        get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddPurchaseBinding.bind(view)
        setupListeners()
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                viewModel.uiState.collect { state ->
                    setupClientDropdown(state)
                    setupMonthDropdown()
                    if (state.isSaved) {
                        findNavController().popBackStack()
                    }
                    binding.tilClient.error = state.error
                    binding.tilMonth.error = state.error
                    binding.tilDate.error = state.error
                    binding.tilLiters.error = state.error
                }
            }
        }
    }
    private fun setupClientDropdown(state: AddPurchaseUiState) {
        val clients = state.clients.map { it.fullName }
        binding.actvClient.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                clients
            )
        )
    }
    private fun setupMonthDropdown() {
        val months = Month.entries.map { month ->
            month.name
        }
        binding.actvMonth.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                months
            )
        )
    }
    private fun setupListeners() {
        binding.actvClient.setOnItemClickListener { _, _, position, _ ->
            val client = viewModel.uiState.value.clients[position]
            viewModel.selectClient(client)
        }
        binding.actvMonth.setOnItemClickListener { _, _, position, _ ->
            val month = Month.entries[position]
            viewModel.selectMonth(month)
        }
        binding.etDate.setOnClickListener {
            showDatePicker()
        }
        binding.etLiters.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                viewModel.setLiters(binding.etLiters.text?.toString().orEmpty())
            }
        }
        binding.rgPaymentStatus.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.rb_paid -> {viewModel.setPaymentStatus(PaymentStatus.PAID)}
                R.id.rb_unpaid -> {viewModel.setPaymentStatus(PaymentStatus.UNPAID)}
            }
        }
        binding.btnSavePurchase.setOnClickListener {
            viewModel.setDate(binding.etDate.text?.toString().orEmpty())
            viewModel.setLiters(binding.etLiters.text?.toString().orEmpty())
            viewModel.savePurchase()
        }
    }
    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText(getString(R.string.select_date))
            .build()
        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            val date = SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
            ).format(Date(selectedDate))
            binding.etDate.setText(date)
            viewModel.setDate(date)
        }
        datePicker.show(
            parentFragmentManager,
            "DATE_PICKER"
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
