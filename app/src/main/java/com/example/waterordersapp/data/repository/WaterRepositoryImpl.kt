package com.example.waterordersapp.data.repository

import com.example.waterordersapp.data.datasource.JsonDataSource
import com.example.waterordersapp.data.mapper.toDTO
import com.example.waterordersapp.data.mapper.toDomain
import com.example.waterordersapp.data.model.WaterDataDTO
import com.example.waterordersapp.domain.model.Client
import com.example.waterordersapp.domain.model.Purchase
import com.example.waterordersapp.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WaterRepositoryImpl @Inject constructor(
    private val dataSource: JsonDataSource
) : WaterRepository {
    private val data = MutableStateFlow(WaterDataDTO())

    override fun getClient(): Flow<List<Client>> {
        return data.map { waterData ->
            waterData.clients.map { it.toDomain() }
        }
    }

    override fun getPurchases(): Flow<List<Purchase>> {
        return data.map { waterData ->
            waterData.purchase.map { it.toDomain() }
        }
    }

    override suspend fun addClient(client: Client) {
        val updateData = data.value.copy(
            clients = data.value.clients + client.toDTO()
        )
        data.value = updateData
        dataSource.saveData(updateData)
    }

    override suspend fun addPurchases(purchase: Purchase) {
        val updateData = data.value.copy(
            purchase = data.value.purchase + purchase.toDTO()
        )
        data.value = updateData
        dataSource.saveData(updateData)
    }
    override suspend fun initialize() {
        data.value = dataSource.loadData()
    }
}