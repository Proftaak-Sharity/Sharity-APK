package com.example.sharity_apk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.data.DataSource
import com.example.sharity_apk.model.ReservationModel

class ReservationAdapter (): RecyclerView.Adapter<ReservationAdapter.ReservationCardViewHolder>() {

    //  Initialize the data using the List found in data/DataSource
    val dataset: List<ReservationModel> = DataSource.reservations


    class ReservationCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
//        val imageView = view?.findViewById<ImageView>(R.id.car_image)
        val reservationNumber = view?.findViewById<TextView>(R.id.reservation_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationCardViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.reservation_card,parent,false)

        return ReservationCardViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int  {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ReservationCardViewHolder, position: Int) {

        val item = dataset[position]
//        holder.imageView?.setImageResource(item.imageResourceId)
        holder.reservationNumber?.text = item.reservationNumber
    }
}