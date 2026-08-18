package com.example.waterordersapp.presentation.addPurchase

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.waterordersapp.R
import com.example.waterordersapp.databinding.FragmentAddPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPurchaseFragment : Fragment(R.layout.fragment_add_purchase) {
    private val viewModel: AddPurchaseViewModel by viewModels()
    private var _binding: FragmentAddPurchaseBinding? = null
    private val binding: FragmentAddPurchaseBinding
        get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddPurchaseBinding.bind(view)
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    println("Clients: ${state.clients}")
                    println("Selected client: ${state.selectedClient}")
                    println("Liters: ${state.liters}")
                    println("Payment status: ${state.paymentStatus}")
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
