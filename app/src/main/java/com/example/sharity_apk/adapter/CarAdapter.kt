package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.data.DataSource
import com.example.sharity_apk.model.CarModel

class CarAdapter(): RecyclerView.Adapter<CarAdapter.CarCardViewHolder>() {

    //  Initialize the data using the List found in data/DataSource
    val dataset: List<CarModel> = DataSource.cars


    class CarCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        val imageView = view?.findViewById<ImageView>(R.id.car_image)
        val carMake = view?.findViewById<TextView>(R.id.car_make)
        val carPrice =  view?.findViewById<TextView>(R.id.car_price)
        val carLocation =  view?.findViewById<TextView>(R.id.car_location)
        val carFuelType =  view?.findViewById<TextView>(R.id.car_fueltype)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarCardViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.car_card,parent,false)

        return CarCardViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int  {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CarCardViewHolder, position: Int) {

        val item = dataset[position]
        holder.imageView?.setImageResource(item.imageResourceId)
        holder.carMake?.text = item.make
        holder.carPrice?.text = "Price: 100 "
        holder.carLocation?.text = "location: Breda "
        holder.carFuelType?.text =  "fuel: Electric "
            }
}
