package com.example.sharity_apk.fragment.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sharity_apk.R
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.databinding.GetReservationDetailsBinding
import com.example.sharity_apk.utils.ImageDecoder
import com.example.sharity_apk.viewmodel.CarViewModel
import com.example.sharity_apk.viewmodel.ReservationViewModel
import kotlinx.coroutines.launch

class GetReservationDetails: Fragment() {

    private var _binding: GetReservationDetailsBinding? = null
    private val binding get() = _binding!!
    private val reservationViewModel: ReservationViewModel by lazy { ViewModelProvider(this)[ReservationViewModel::class.java] }
    private val carViewModel: CarViewModel by lazy { ViewModelProvider(this)[CarViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetReservationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SharityPreferences(requireContext())

//        Connect textfields to variables:
        val reservationStart: TextView = binding.reservationStartDb
        val reservationEnd: TextView = binding.reservationEndDb
        val reservationDate: TextView = binding.reservationDateDb

        val make: TextView = binding.makeDb
        val model: TextView = binding.carModelDb

        val licensePlate: TextView = binding.licensePlateDb
        val kmPackage: TextView = binding.kmPackageDb
        val packagePrice: TextView = binding.kmPackagePriceDb
        val totalPrice: TextView = binding.totalPriceDb
        val paymentStatus: TextView = binding.paymentStatusDb

        viewLifecycleOwner.lifecycleScope.launch {

//            connecting reservation number from shared preference to variable
            val reservationNumber = preferences.getReservationNumber()

//            using shared preference to retrieve reservation data from api
            val reservation = reservationViewModel.getReservation(reservationNumber)

            //      connecting licenseplate from shared preference to variable
            val licensePlateCar = preferences.getLicensePlate()
            val car = carViewModel.getCar(licensePlateCar)
//            connecting reservation api-data to textfield

            when (val encodedString = carViewModel.getCarImage(car.licensePlate.toString()).image) {
                "1" -> binding.imageCar.setImageResource(R.drawable.volvo_xc90)
                "2" -> binding.imageCar.setImageResource(R.drawable.landrover_defender)
                "3" -> binding.imageCar.setImageResource(R.drawable.tesla_3)
                "4" -> binding.imageCar.setImageResource(R.drawable.ford_mustang_convertible)
                "5" -> binding.imageCar.setImageResource(R.drawable.cupra_leon)
                "6" -> binding.imageCar.setImageResource(R.drawable.mercedes_r350_amg)
                "7" -> binding.imageCar.setImageResource(R.drawable.ferrari_testarossa)
                "8" -> binding.imageCar.setImageResource(R.drawable.opel_vectra)
                "9" -> binding.imageCar.setImageResource(R.drawable.toyota_mirai)
                else -> {
                    val imageCar = ImageDecoder().decodeImageString(encodedString)
                    binding.imageCar.setImageBitmap(imageCar)
                }
            }

            reservationStart.text = reservation.startDate
            reservationEnd.text = reservation.endDate
            reservationDate.text = reservation.reservationDate

            make.text = car.make
            model.text = car.model

            licensePlate.text = reservation.licensePlate
            kmPackage.text = reservation.kmPackage.toString() + " km"
            packagePrice.text = "€ ${"%.2f".format(reservation.packagePrice)}"
            totalPrice.text = "€ ${"%.2f".format(reservation.rent)}"
            paymentStatus.text = reservation.payment

            when (reservation.payment) {
                "OPEN" -> binding.btnPay.isVisible = true
                else -> binding.btnPay.isVisible = false
            }

            binding.btnPay.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    val newStatus = reservationViewModel.updatePayment(
                        reservation.reservationNumber,
                        getString(R.string.paid)
                    )
                    binding.btnPay.isVisible = false
                    paymentStatus.text = newStatus
                }
            }

            //Button bindings:
            binding.btnfindCaronMap.setOnClickListener {
                    findNavController().navigate(R.id.action_GetReservationDetails_to_mapsFragment3)
              }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
