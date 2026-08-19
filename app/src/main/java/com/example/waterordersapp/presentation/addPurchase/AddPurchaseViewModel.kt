package com.example.waterordersapp.presentation.addPurchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterordersapp.domain.model.Client
import com.example.waterordersapp.domain.model.Month
import com.example.waterordersapp.domain.model.PaymentStatus
import com.example.waterordersapp.domain.model.Purchase
import com.example.waterordersapp.domain.usecase.AddPurchaseUseCase
import com.example.waterordersapp.domain.usecase.GetClientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPurchaseViewModel @Inject constructor(
    private val getClientUseCase: GetClientsUseCase,
    private val addPurchaseUseCase: AddPurchaseUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddPurchaseUiState())
    val uiState: StateFlow<AddPurchaseUiState> = _uiState.asStateFlow()
    init {
        loadClients()
    }

    private fun loadClients() {
        viewModelScope.launch {
            getClientUseCase().collect { clients ->
                _uiState.value = _uiState.value.copy(clients = clients)
            }
        }
    }
    fun selectClient(client: Client) {
        _uiState.value = _uiState.value.copy(selectedClient = client)
    }
    fun selectMonth(month: Month) {
        _uiState.value = _uiState.value.copy(selectedMonth = month)
    }
    fun setDate(date: String) {
        _uiState.value = _uiState.value.copy(date = date)
    }
    fun setLiters(liters: String) {
        _uiState.value = _uiState.value.copy(liters = liters)
    }
    fun setPaymentStatus(paymentStatus: PaymentStatus) {
        _uiState.value = _uiState.value.copy(
            paymentStatus = paymentStatus
        )
    }
    fun savePurchase() {
        val state = _uiState.value
        if(state.selectedClient == null) {
            _uiState.value = state.copy(error = "Selected Client")
            return
        }
        val liters = state.liters.toDoubleOrNull()
        if(liters == null || liters <= 0) {
            _uiState.value = state.copy(error = "Enter the correct amount of water")
            return
        }

        if(state.date.isBlank()) {
            _uiState.value = state.copy(error = "Specify the date")
            return
        }
        viewModelScope.launch {
            try {
                val purchase = Purchase(
                    id = System.currentTimeMillis(),
                    clientId = state.selectedClient.id,
                    month = state.selectedMonth,
                    date = state.date,
                    liters = liters,
                    paymentStatus = state.paymentStatus
                )
                addPurchaseUseCase(purchase)
                _uiState.value = _uiState.value.copy(
                    isSaved = true,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }

    }
}