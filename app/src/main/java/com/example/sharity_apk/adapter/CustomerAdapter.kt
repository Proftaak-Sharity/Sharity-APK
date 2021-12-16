package com.example.sharity_apk.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.model.Customer

class CustomerAdapter(val customer: MutableList<Customer>): RecyclerView.Adapter<CustomerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_customer, parent, false)

        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        return holder.bindView(customer[position])
    }

    override fun getItemCount(): Int {
        return customer.size
    }
}


class CustomerViewHolder(itemView: View): RecyclerView.ViewHolder (itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.tvName)
    private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)

    fun bindView(customer: Customer) {
        tvName.text = customer.dateOfBirth
        tvEmail.text = customer.email




    }


}
