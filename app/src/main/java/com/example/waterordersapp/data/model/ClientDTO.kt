package com.example.waterordersapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ClientDTO (
    val id: Long,
    val fullName: String
)