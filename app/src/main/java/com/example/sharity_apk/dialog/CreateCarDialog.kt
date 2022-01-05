package com.example.sharity_apk.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.R
import com.example.sharity_apk.config.SharityPreferences

class CreateCarDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.create_car_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        
        rootView.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dismiss()
        }

        rootView.findViewById<Button>(R.id.btn_add).setOnClickListener {

            val preferences = SharityPreferences(requireContext())

            when (rootView.findViewById<RadioGroup>(R.id.carTypeRadioGroup).checkedRadioButtonId) {
                R.id.electric -> {
                    preferences.setAddedCarType(getString(R.string.electric))
                    findNavController().navigate(R.id.action_GetAllCars_to_CreateCar)
                    dismiss()
                }
                R.id.hydrogen -> {
                    preferences.setAddedCarType(getString(R.string.hydrogen))
                    findNavController().navigate(R.id.action_GetAllCars_to_CreateCar)
                    dismiss()
                }
                R.id.fuel -> {
                    preferences.setAddedCarType(getString(R.string.fuel))
                    findNavController().navigate(R.id.action_GetAllCars_to_CreateCar)
                    dismiss()
                }
                else -> Toast.makeText(requireContext(), "Select a car type", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

}