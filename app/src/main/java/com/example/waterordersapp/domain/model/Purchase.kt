package com.example.waterordersapp.domain.model

data class Purchase(
    val id: Long,
    val clientId: Long,
    val month: Month,
    val date: String,
    val liters: Double,
    val paymentStatus: PaymentStatus
)