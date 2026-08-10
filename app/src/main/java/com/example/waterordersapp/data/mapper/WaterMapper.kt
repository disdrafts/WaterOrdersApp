package com.example.waterordersapp.data.mapper

import com.example.waterordersapp.data.model.ClientDTO
import com.example.waterordersapp.data.model.PurchaseDTO
import com.example.waterordersapp.domain.model.Client
import com.example.waterordersapp.domain.model.Month
import com.example.waterordersapp.domain.model.PaymentStatus
import com.example.waterordersapp.domain.model.Purchase

fun ClientDTO.toDomain() : Client {
    return Client(
        id = id,
        fullName = fullName
    )
}
fun Client.toDTO() : ClientDTO {
    return ClientDTO(
        id = id,
        fullName = fullName
    )
}
fun PurchaseDTO.toDomain() : Purchase {
    return Purchase(
        id = id,
        clientId = clientId,
        month = Month.valueOf(month),
        date = date,
        liters = liters,
        paymentStatus = PaymentStatus.valueOf(paymentStatus)
    )
}
fun Purchase.toDTO() : PurchaseDTO {
    return PurchaseDTO(
        id = id,
        clientId = clientId,
        month = month.name,
        date = date,
        liters = liters,
        paymentStatus = paymentStatus.name
    )
}