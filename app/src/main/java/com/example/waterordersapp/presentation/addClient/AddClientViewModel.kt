package com.example.waterordersapp.presentation.addClient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterordersapp.R
import com.example.waterordersapp.domain.model.Client
import com.example.waterordersapp.domain.usecase.AddClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddClientViewModel @Inject constructor(
    private val addClientUseCase: AddClientUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddClientUiState())
    val uiState: StateFlow<AddClientUiState> = _uiState.asStateFlow()

    fun addClient(fullName: String) {
        if (fullName.isBlank()) {
            _uiState.value = AddClientUiState(error(R.string.empty_full_name_error))
            return
        }
        viewModelScope.launch {
            _uiState.value = AddClientUiState(isLoading = true)
            try {
                val client = Client(
                    id = System.currentTimeMillis(),
                    fullName = fullName
                )
                addClientUseCase(client)
                _uiState.value = AddClientUiState(isSaved = true)
            } catch (e: Exception) {
                _uiState.value = AddClientUiState(error = e.message)
            }
        }
    }

}