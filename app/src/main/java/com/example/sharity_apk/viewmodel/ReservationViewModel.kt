package com.example.sharity_apk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharity_apk.model.ReservationModel
import com.example.sharity_apk.service.ReservationApiService
import com.example.sharity_apk.service.ServiceGenerator

class ReservationViewModel: ViewModel() {

    private val serviceGenerator = ServiceGenerator.buildService(ReservationApiService::class.java)

    suspend fun getReservations(customerNumber: Long) : MutableList<ReservationModel> {
        return serviceGenerator.getReservations(customerNumber)
    }

    suspend fun getReservation(reservationNumber: Int) : ReservationModel {
        return serviceGenerator.getReservation(reservationNumber)
    }

    suspend fun addReservation(customerNumber: Long?, licensePlate: String?, kmPackage: Int?, startDate: String?, endDate: String?, rent: Double?, packagePrice: Double?, paymentEnum: String?) : Int {
        return serviceGenerator.addReservation(customerNumber, licensePlate, kmPackage, startDate, endDate, rent, packagePrice, paymentEnum)
    }

    suspend fun updatePayment(reservationNumber: Int, paymentEnum: String?) : String {
        return serviceGenerator.updatePayment(reservationNumber, paymentEnum)
    }





}