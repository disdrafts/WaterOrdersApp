package com.example.waterordersapp.domain.usecase

import com.example.waterordersapp.domain.model.Client
import com.example.waterordersapp.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientsUseCase @Inject constructor(
    private val repository: WaterRepository
) {
    operator fun invoke() : Flow<List<Client>> {
        return repository.getClient()
    }
}