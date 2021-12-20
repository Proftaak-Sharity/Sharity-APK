package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.model.CustomerModel

import com.example.sharity_apk.model.ReservationModel

class ReservationAdapter(
    private val reservationModel: MutableList<ReservationModel>,
//    private val listener: (ReservationModel) -> Unit
     ): RecyclerView.Adapter<ReservationAdapter.ReservationCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_card, parent, false)

        return ReservationCardViewHolder(view)
//        {
//            listener(reservationModel[it])
//        }
    }
//
    override fun onBindViewHolder(holder: ReservationCardViewHolder, position: Int) {
     return holder.bindView(reservationModel[position])
    }

    override fun getItemCount(): Int {
        return reservationModel.size
    }


class ReservationCardViewHolder(itemView: View
//                                listener: (Int) -> Unit
): RecyclerView.ViewHolder (itemView) {
    private val tvReservationNumber: TextView = itemView.findViewById(R.id.tv_reservation_number)
    private val tvRent: TextView = itemView.findViewById(R.id.tv_rent)

//    init {
//        itemView.setOnClickListener {
//            listener (adapterPosition)



    fun bindView(reservationModel: ReservationModel) {
        tvReservationNumber.text = reservationModel.reservationNumber.toString()
        tvRent.text = reservationModel.rent.toString()
    }
}}



