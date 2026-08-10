package com.example.waterordersapp.presentation.home

data class HomeUiState(
    val purchases: List<PurchaseUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)