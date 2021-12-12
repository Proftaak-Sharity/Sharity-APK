package com.example.sharity_apk.adapter

import android.content.Context
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.Reservation

class ReservationItemAdapter (
    private val context: Context,
    private val dataset: List<Reservation>
    ) : RecyclerView.Adapter<ReservationItemAdapter.ReservationItemViewHolder>() {
    class ReservationItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.reservation_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.listreservation_item, parent, false)

        return ReservationItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ReservationItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text =  context.resources.getString(item.stringResourceId)
    }

    override fun getItemCount() = dataset.size
}