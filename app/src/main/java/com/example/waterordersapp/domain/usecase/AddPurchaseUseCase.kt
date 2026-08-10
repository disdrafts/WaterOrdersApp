package com.example.waterordersapp.domain.usecase

import com.example.waterordersapp.domain.model.Purchase
import com.example.waterordersapp.domain.repository.WaterRepository
import javax.inject.Inject

class AddPurchaseUseCase @Inject constructor(
    private val repository: WaterRepository
) {
    suspend operator fun invoke(purchase: Purchase) {
        repository.addPurchases(purchase)
    }
}