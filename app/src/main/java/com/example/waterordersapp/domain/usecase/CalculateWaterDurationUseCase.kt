package com.example.waterordersapp.domain.usecase

import javax.inject.Inject

class CalculateWaterDurationUseCase @Inject constructor(){
    companion object {
        private const val DAILY_WATER_CONSUMPTION = 2.0
    }
    operator fun invoke(liters: Double) : Int {
        if(liters <= 0) return 0

        return(liters / DAILY_WATER_CONSUMPTION).toInt()
    }
}