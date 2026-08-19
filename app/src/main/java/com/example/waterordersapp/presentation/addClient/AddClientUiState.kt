package com.example.waterordersapp.presentation.addClient

data class AddClientUiState(
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)