package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.GetAllReservations
import com.example.sharity_apk.R
import com.example.sharity_apk.model.ReservationModel

class ReservationAdapter(
    private val reservationList: MutableList<ReservationModel>,
    private val listener: OnReservationClickListener
):
    RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_card, parent, false)

        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {

        val currentReservation = reservationList[position]
        holder.tvReservationNumber.text = currentReservation.reservationNumber.toString()
        holder.tvRent.text = currentReservation.rent.toString()

    }

    override fun getItemCount(): Int {
        return reservationList.size
    }

    inner class ReservationViewHolder(itemView: View): RecyclerView.ViewHolder (itemView),
        View.OnClickListener {

        val tvReservationNumber: TextView = itemView.findViewById(R.id.tv_reservation_number)
        val tvRent: TextView = itemView.findViewById(R.id.tv_rent)

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

    interface OnReservationClickListener {
        fun onItemClick(position: Int)
    }
}



