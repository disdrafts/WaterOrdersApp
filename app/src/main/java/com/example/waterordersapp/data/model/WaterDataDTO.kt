package com.example.waterordersapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WaterDataDTO(
    val clients: List<ClientDTO> = emptyList(),
    val purchase: List<PurchaseDTO> = emptyList()
)