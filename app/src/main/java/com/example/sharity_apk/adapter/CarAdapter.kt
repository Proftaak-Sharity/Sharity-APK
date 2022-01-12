package com.example.sharity_apk.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.example.sharity_apk.utils.ImageDecoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarAdapter(
    private val carList: MutableList<CarModel>,
    private val listener: OnCarClickListener
):
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.car_card, parent, false)
        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {

        val currentCar = carList[position]

        CoroutineScope(Dispatchers.Main).launch {

            val serviceGenerator = ServiceGenerator.buildService(CarApiService::class.java)

            when (val encodedString = serviceGenerator.getCarImage(currentCar.licensePlate.toString()).image) {
                "1" -> holder.ivCar.setImageResource(R.drawable.volvo_xc90)
                "2" -> holder.ivCar.setImageResource(R.drawable.landrover_defender)
                "3" -> holder.ivCar.setImageResource(R.drawable.tesla_3)
                "4" -> holder.ivCar.setImageResource(R.drawable.ford_mustang_convertible)
                "5" -> holder.ivCar.setImageResource(R.drawable.cupra_leon)
                "6" -> holder.ivCar.setImageResource(R.drawable.mercedes_r350_amg)
                "7" -> holder.ivCar.setImageResource(R.drawable.ferrari_testarossa)
                "8" -> holder.ivCar.setImageResource(R.drawable.opel_vectra)
                "9" -> holder.ivCar.setImageResource(R.drawable.toyota_mirai)
                else -> {
                    val imageCar = ImageDecoder().decodeImageString(encodedString)
                    holder.ivCar.setImageBitmap(imageCar)
                }
            }

            holder.tvMake.text = currentCar.make
            holder.tvModel.text = currentCar.model
            "€ ${"%.2f".format(currentCar.pricePerDay?.toDouble())} per day".also { holder.tvPrice.text = it }



            if (currentCar.customerNumber != null) {
                val carCity =  getCustomerCity(currentCar.customerNumber)
                holder.tvLocation.text = carCity
            } else {
                holder.tvLocation.setText(R.string.location_unknown)
            }
        }

        if ( currentCar.batteryCapacity != null) {
            holder.tvCarType.setText(R.string.electric)
        } else if (currentCar.fuelType != null) {
            holder.tvCarType.text = currentCar.fuelType
        } else if (currentCar.kmPerKilo != null) {
            holder.tvCarType.setText(R.string.hydrogen)
        } else {
            holder.tvCarType.setText(R.string.cartype_not_found)
        }
    }

    private suspend fun getCustomerCity(customerNumber: Long): String? {
        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        return  (serviceGenerator.getCustomer(customerNumber)).city
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val ivCar: ImageView = itemView.findViewById(R.id.ivCar)
        val tvMake: TextView = itemView.findViewById(R.id.tvMake)
        val tvModel: TextView = itemView.findViewById(R.id.tvModel)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvCarType: TextView = itemView.findViewById(R.id.tvCarType)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)

            }
        }
    }

    interface OnCarClickListener {
        fun onItemClick(position: Int)
    }

}



