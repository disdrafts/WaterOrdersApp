package com.example.waterordersapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterordersapp.R
import com.example.waterordersapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)
    private val purchaseAdapter = PurchaseAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setupRecyclerView()
        setupListeners()
        observeState()
    }
    private fun setupRecyclerView() {
        binding.rvPurchases.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = purchaseAdapter
        }
    }
    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                viewModel.uiState.collect { state ->
                    purchaseAdapter.submitList(state.purchases)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setupListeners() {
        binding.btnAddClient.setOnClickListener {
            findNavController().navigate(R.id.addClientFragment)
        }
        binding.btnAddPurchase.setOnClickListener {
            findNavController().navigate(R.id.addPurchaseFragment)
        }
    }
}