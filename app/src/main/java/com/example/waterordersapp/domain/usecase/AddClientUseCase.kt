package com.example.waterordersapp.domain.usecase

import com.example.waterordersapp.domain.model.Client
import com.example.waterordersapp.domain.repository.WaterRepository
import javax.inject.Inject

class AddClientUseCase @Inject constructor(
    private val repository: WaterRepository
) {
    suspend operator fun invoke(client: Client) {
        repository.addClient(client)
    }
}