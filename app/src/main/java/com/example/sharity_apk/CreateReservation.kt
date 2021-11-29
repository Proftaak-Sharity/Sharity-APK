package com.example.sharity_apk

import com.google.android.material.datepicker.MaterialDatePicker

class CreateReservation {
    val dateRangePicker =
        MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select dates")
            .build()
}