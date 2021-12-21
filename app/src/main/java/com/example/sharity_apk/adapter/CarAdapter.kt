package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.model.CarModel
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarAdapter(
    private val carList: MutableList<CarModel>,
    private val listener: OnCarClickListener

):
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.car_card, parent, false)

        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {

        val currentCar = carList[position]

        holder.ivCar.setImageResource(R.drawable.ferrari_testarossa)
        holder.tvMake.text = currentCar.make
        holder.tvModel.text = currentCar.model
        holder.tvPrice.text = currentCar.pricePerDay

        CoroutineScope(Dispatchers.IO).launch {
            var carCity =  getCustomerCity(currentCar.customerNumber!!)
            holder.tvLocation.text = carCity
        }

        if ( currentCar.batteryCapacity != null) {
            holder.tvCarType.text = "Electric"
        } else if (currentCar.fuelType != null) {
            holder.tvCarType.text = currentCar.fuelType
        } else if (currentCar.kmPerKilo != null) {
            holder.tvCarType.text = "Hydrogen"
        } else {
            holder.tvCarType.text = "carType not found"
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

        val ivCar: ImageView = itemView?.findViewById(R.id.ivCar)
        val tvMake: TextView = itemView?.findViewById(R.id.tvMake)
        val tvModel: TextView = itemView?.findViewById(R.id.tvModel)
        val tvPrice: TextView = itemView?.findViewById(R.id.tvPrice)
        val tvLocation: TextView = itemView?.findViewById(R.id.tvLocation)
        val tvCarType: TextView = itemView?.findViewById(R.id.tvCarType)

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




