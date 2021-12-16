package com.example.sharity_apk.data

import com.example.sharity_apk.R
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.model.ReservationModel

object DataSource {
    val cars: List<CarModel> = listOf(
        CarModel(
            R.drawable.opel_astra,
            "Opel",
        ),
        CarModel(
            R.drawable.mazda_mx3,
            "Mazda",
        ),
        CarModel(
            R.drawable.ferrari_testarossa,
            "Ferrari",
        ),
        CarModel(
            R.drawable.seat_leon,
            "Seat",
        ),
        CarModel(
            R.drawable.volvo_v40,
            "Volvo",
        ),
        CarModel(
            R.drawable.suzuki_swift,
            "Suzuki",
        )
    )
    val reservations: List<ReservationModel> = listOf(
        ReservationModel(
            "Reservering 000001",
        "KNTK01",
     3,
    607,
    1000,
    210,
    "2021-11-04",
    "2021-12-01",
    "2021-12-05",
    "OPEN"
        )
    )
}