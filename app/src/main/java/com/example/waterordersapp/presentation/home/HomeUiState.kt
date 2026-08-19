package com.example.waterordersapp.presentation.home

import com.example.waterordersapp.domain.model.Client

data class HomeUiState(
    val clients: List<Client> = emptyList(),
    val purchases: List<PurchaseUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)