package com.example.waterordersapp.presentation.home

import com.example.waterordersapp.domain.model.Month
import com.example.waterordersapp.domain.model.PaymentStatus

data class PurchaseUiModel(
    val clientName: String,
    val month: Month,
    val date: String,
    val liters: Double,
    val daysEnough: Int,
    val paymentStatus: PaymentStatus
)