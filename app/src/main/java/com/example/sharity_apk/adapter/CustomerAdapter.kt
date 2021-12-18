package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.model.CustomerModel

class CustomerAdapter(val customerModel: MutableList<CustomerModel>): RecyclerView.Adapter<CustomerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_customer, parent, false)

        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        return holder.bindView(customerModel[position])
    }

    override fun getItemCount(): Int {
        return customerModel.size
    }
}


class CustomerViewHolder(itemView: View): RecyclerView.ViewHolder (itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.tvName)
    private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)

    fun bindView(customerModel: CustomerModel) {
        tvName.text = customerModel.lastName
        tvEmail.text = customerModel.email




    }


}
