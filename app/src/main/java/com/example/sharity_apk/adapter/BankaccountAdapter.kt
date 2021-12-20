package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.model.BankaccountModel

class BankaccountAdapter(
    private val bankaccountList: MutableList<BankaccountModel>,
    private val listener: OnBankaccountClickListener
):
    RecyclerView.Adapter<BankaccountAdapter.BankaccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankaccountViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_bankaccount, parent, false)

        return BankaccountViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BankaccountViewHolder, position: Int) {

        val currentBankaccount = bankaccountList[position]

        holder.tvIban.text = currentBankaccount.iban
        holder.tvAccountHolder.text = currentBankaccount.accountHolder
    }

    override fun getItemCount(): Int {
        return bankaccountList.size
    }

    inner class BankaccountViewHolder(itemView: View): RecyclerView.ViewHolder (itemView),
        View.OnClickListener {

        val tvIban: TextView = itemView.findViewById(R.id.tvIban)
        val tvAccountHolder: TextView = itemView.findViewById(R.id.tvAccountHolder)

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

    interface OnBankaccountClickListener {
        fun onItemClick(position: Int)
    }
}

