package com.example.waterordersapp.domain.repository

import com.example.waterordersapp.domain.model.Client
import com.example.waterordersapp.domain.model.Purchase
import kotlinx.coroutines.flow.Flow

interface WaterRepository {
    fun getClient(): Flow<List<Client>>
    fun getPurchases() : Flow<List<Purchase>>
    suspend fun addClient(client: Client)
    suspend fun addPurchases(purchase: Purchase)
    suspend fun initialize()
}