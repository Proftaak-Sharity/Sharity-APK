package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        if (currentBankaccount.iban?.contains("ABNA") == true) {
            holder.ivBankCard.setImageResource(R.drawable.abn_amro)
        } else if (currentBankaccount.iban?.contains("RABO") == true) {
            holder.ivBankCard.setImageResource(R.drawable.rabobank)
        } else if (currentBankaccount.iban?.contains("INGB") == true) {
            holder.ivBankCard.setImageResource(R.drawable.ing)
        } else if (currentBankaccount.iban?.contains("BUNQ") == true) {
            holder.ivBankCard.setImageResource(R.drawable.bunq)
        } else if (currentBankaccount.iban?.contains("KNAB") == true) {
            holder.ivBankCard.setImageResource(R.drawable.knab)
        } else if (currentBankaccount.iban?.contains("SNSB") == true) {
            holder.ivBankCard.setImageResource(R.drawable.sns)
        } else {
            holder.ivBankCard.setImageResource(R.drawable.unknown_bank)
        }
        holder.tvIban.text = currentBankaccount.iban
        holder.tvAccountHolder.text = currentBankaccount.accountHolder
    }

    override fun getItemCount(): Int {
        return bankaccountList.size
    }

    inner class BankaccountViewHolder(itemView: View): RecyclerView.ViewHolder (itemView),
        View.OnClickListener {

        val ivBankCard: ImageView = itemView.findViewById(R.id.image_bankcard)
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

