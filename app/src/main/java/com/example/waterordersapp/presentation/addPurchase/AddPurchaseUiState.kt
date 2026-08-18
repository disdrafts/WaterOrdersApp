package com.example.waterordersapp.presentation.addPurchase

import com.example.waterordersapp.domain.model.Client
import com.example.waterordersapp.domain.model.Month
import com.example.waterordersapp.domain.model.PaymentStatus

data class AddPurchaseUiState(
    val clients: List<Client> = emptyList(),
    val selectedClient: Client? = null,
    val selectedMonth: Month = Month.JANUARY,
    val date: String = "",
    val liters: String = "",
    val paymentStatus: PaymentStatus = PaymentStatus.PAID,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)