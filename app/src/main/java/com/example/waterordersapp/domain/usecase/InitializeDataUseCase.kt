package com.example.waterordersapp.domain.usecase

import com.example.waterordersapp.domain.repository.WaterRepository
import javax.inject.Inject

class InitializeDataUseCase @Inject constructor(
    private val repository: WaterRepository
) {
    suspend operator fun invoke() {
        repository.initialize()
    }
}