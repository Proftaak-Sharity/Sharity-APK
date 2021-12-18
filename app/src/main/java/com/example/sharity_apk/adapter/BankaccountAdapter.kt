package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.model.BankaccountModel
import com.google.android.material.card.MaterialCardView

class BankaccountAdapter(val bankaccountModel: MutableList<BankaccountModel>): RecyclerView.Adapter<BankaccountViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankaccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_bankaccount, parent, false)

        return BankaccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankaccountViewHolder, position: Int) {
        return holder.bindView(bankaccountModel[position])
    }

    override fun getItemCount(): Int {
        return bankaccountModel.size
    }
}


class BankaccountViewHolder(itemView: View): RecyclerView.ViewHolder (itemView) {
    private val tvIban: TextView = itemView.findViewById(R.id.tvIban)
    private val tvAccountHolder: TextView = itemView.findViewById(R.id.tvAccountHolder)

    fun bindView(bankaccountModel: BankaccountModel) {
        tvIban.text = bankaccountModel.iban
        tvAccountHolder.text = bankaccountModel.accountHolder
    }
}