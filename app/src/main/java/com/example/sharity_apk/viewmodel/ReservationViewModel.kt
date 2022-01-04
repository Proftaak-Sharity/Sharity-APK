package com.example.sharity_apk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharity_apk.model.ReservationModel
import com.example.sharity_apk.service.ReservationApiService
import com.example.sharity_apk.service.ServiceGenerator

class ReservationViewModel: ViewModel() {

    private val serviceGenerator = ServiceGenerator.buildService(ReservationApiService::class.java)


    suspend fun addReservation( customerNumber: Long?,
                                licensePlate: String?,
                                kmPackage: Int?,
                                startDate: String?,
                                endDate: String?,
                                rent: Double?,
                                packagePrice: Double?,
                                paymentEnum: String?

                                ) : Int{
        val reservationNumber: Int =  serviceGenerator.addReservation(customerNumber,
                                        licensePlate,
                                        kmPackage,
                                        startDate,
                                        endDate,
                                        rent,
                                        packagePrice,
                                        paymentEnum
        )
        return reservationNumber
    }
}