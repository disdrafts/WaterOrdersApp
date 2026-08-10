package com.example.waterordersapp.data.datasource

import android.content.Context
import com.example.waterordersapp.data.model.WaterDataDTO
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class JsonDataSource @Inject constructor(
    private val context: Context,
    private val json: Json
) {
    companion object {
        const val FILE_NAME = "water_data.json"
    }
    private val file: File
        get() = File(context.filesDir, FILE_NAME)

    suspend fun loadData() : WaterDataDTO {
        if(!file.exists()) {
            return WaterDataDTO()
        }
        val jsonString = file.readText()
        if(jsonString.isBlank()) {
            return WaterDataDTO()
        }
        return json.decodeFromString<WaterDataDTO>(jsonString)
    }
    suspend fun saveData(data: WaterDataDTO) {
        val jsonString = json.encodeToString(data)
        file.writeText(jsonString)
    }
}