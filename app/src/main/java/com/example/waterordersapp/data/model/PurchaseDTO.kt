package com.example.waterordersapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PurchaseDTO (
    val id: Long,
    val clientId: Long,
    val month: String,
    val date: String,
    val liters: Double,
    val paymentStatus: String

)