package com.example.waterordersapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterordersapp.domain.usecase.CalculateWaterDurationUseCase
import com.example.waterordersapp.domain.usecase.GetClientsUseCase
import com.example.waterordersapp.domain.usecase.GetPurchasesUseCase
import com.example.waterordersapp.domain.usecase.InitializeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getClientUseCase: GetClientsUseCase,
    private val getPurchasesUseCase: GetPurchasesUseCase,
    private val initializerDataUseCase: InitializeDataUseCase,
    private val calculateWaterDurationUseCase: CalculateWaterDurationUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }
    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            try {
                initializerDataUseCase()
                combine(
                    getClientUseCase(),
                    getPurchasesUseCase()
                ) { clients, purchases ->
                    val purchaseUiModels = purchases.map { purchase ->
                        val client = clients.firstOrNull {
                            it.id == purchase.clientId
                        }
                        PurchaseUiModel(
                            clientName = client?.fullName.orEmpty(),
                            month = purchase.month,
                            date = purchase.date,
                            liters = purchase.liters,
                            daysEnough = calculateWaterDurationUseCase(
                                purchase.liters
                            ),
                            paymentStatus = purchase.paymentStatus
                        )
                    }
                    HomeUiState(
                        clients = clients,
                        purchases = purchaseUiModels,
                        isLoading = false
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}