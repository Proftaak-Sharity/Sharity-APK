package com.example.sharity_apk

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ReservationApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.example.sharity_apk.utils.GPSUtils
import com.example.sharity_apk.utils.GPSUtils.latitude
import com.example.sharity_apk.utils.GPSUtils.longitude

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
       GPSUtils.initPermissions(requireActivity())

        ///userlocation
//try {
    GPSUtils.findDeviceLocation(requireActivity())
    val lng = longitude
    val lat = latitude

     val yourLocation = LatLng(lat!!, lng!!)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 12f))
            googleMap.addMarker(MarkerOptions().position(yourLocation).title("You are here!"))
//} catch (e:Exception){
//    Toast.makeText(requireContext(), "Failed to catch location", Toast.LENGTH_SHORT).show()
//}
            googleMap.uiSettings.apply {
                isZoomControlsEnabled = true
                isMyLocationButtonEnabled = true
            }
     //looking for the car and customer adress

        val preferences = SharityPreferences(requireContext())
        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)

//      connecting reservation number from shared preference to variable
        val reservationNumber = preferences.getReservationNumber()
        val licensePlate = preferences.getReservationLicensePlate()

//   using shared preference to retrieve reservation data from api
//        val customer = serviceGenerator.getCustomerByLicensePlate(licensePlate)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
         }
    }


