package com.example.waterordersapp.presentation.addClient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.waterordersapp.R
import com.example.waterordersapp.databinding.FragmentAddClientBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddClientFragment : Fragment(R.layout.fragment_add_client) {
    private val viewModel: AddClientViewModel by viewModels()
    private var _binding: FragmentAddClientBinding? = null
    private val binding: FragmentAddClientBinding
        get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddClientBinding.bind(view)
        setupListeners()
        observeState()
    }

    private fun setupListeners() {
        binding.btnSaveClient.setOnClickListener {
            val fullName = binding.etFullName
                .text
                ?.toString()
                .orEmpty()
            viewModel.addClient(fullName)
        }
    }
    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tilFullName.error = state.error
                    if(state.isSaved) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}