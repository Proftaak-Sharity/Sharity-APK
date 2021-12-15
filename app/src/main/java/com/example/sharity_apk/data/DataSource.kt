package com.example.sharity_apk.data

import com.example.sharity_apk.R
import com.example.sharity_apk.Reservation
import com.example.sharity_apk.model.SearchedCar

class Datasource {
    fun loadReservations(): List<Reservation> {
        return listOf<Reservation>(

            Reservation(R.string.reservation1),
            Reservation(R.string.reservation2),
            Reservation(R.string.reservation3),
            Reservation(R.string.reservation4),
            Reservation(R.string.reservation5),
            Reservation(R.string.reservation6),
            Reservation(R.string.reservation7)
            )
    }

    fun loadSearchedCars(): List<SearchedCar> {
        return listOf<SearchedCar>(

            SearchedCar(R.string.reservation1),
            SearchedCar(R.string.reservation2),
            SearchedCar(R.string.reservation3),
            SearchedCar(R.string.reservation4),
            SearchedCar(R.string.reservation5),
            SearchedCar(R.string.reservation6),
            SearchedCar(R.string.reservation7)
        )
    }

}