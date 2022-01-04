package com.example.sharity_apk

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.sharity_apk.config.SharityPreferences
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.example.sharity_apk.utils.GPSUtils
import com.example.sharity_apk.utils.GPSUtils.latitude
import com.example.sharity_apk.utils.GPSUtils.longitude
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
        GPSUtils.findDeviceLocation(requireActivity())
        val lng = longitude
        val lat = latitude

        val yourLocation = LatLng(lat!!, lng!!)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15f))

        googleMap.addMarker(MarkerOptions().position(yourLocation).title("You are here!"))

        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
        }
        //looking for the car and customer adress
        val preferences = SharityPreferences(requireContext())
        val serviceGenerator = ServiceGenerator.buildService(CarApiService::class.java)
        val serviceGenerator2 = ServiceGenerator.buildService(CustomerApiService::class.java)

//      connecting licenseplate from shared preference to variable
        val licensePlate = preferences.getReservationLicensePlate()

//   using shared preference to retrieve reservation data from api
        viewLifecycleOwner.lifecycleScope.launch {
            val car = serviceGenerator.getCar(licensePlate)
            val customerNumber = car.customerNumber
            val customer = serviceGenerator2.getCustomer(customerNumber!!)

            val customerAddress = customer.address + customer.houseNumber + ", " + customer.city

            //         toast to see the address of the car - can be deleted when marker works
//            Toast.makeText(requireContext(), customerAddress, Toast.LENGTH_LONG).show()

           val geoCoder = Geocoder(
                context
            )
                val addressList = geoCoder.getFromLocationName(
                    customerAddress,
                    1
                )

                if (addressList != null && addressList.size > 0) {
                    val address = addressList.get(0) as Address
                    val customerLat = address.latitude
                    val customerLng = address.longitude

                    val customerLatLng = LatLng(customerLat, customerLng)

                    val bounds = LatLngBounds.builder()
                        .include(yourLocation)
                        .include(customerLatLng)
                        .build()
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(customerLatLng)
                            .title("You rentalcar is here!")
                    )

            }
        }
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