package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.databinding.CardBankaccountBinding
import com.example.sharity_apk.model.BankaccountModel

class BankaccountAdapter(private val onItemClicked: (BankaccountModel) -> Unit
): ListAdapter<BankaccountModel, BankaccountAdapter.BankaccountViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BankaccountModel>() {
            override fun areItemsTheSame(
                oldItem: BankaccountModel,
                newItem: BankaccountModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BankaccountModel,
                newItem: BankaccountModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : BankaccountViewHolder {
        val viewHolder = BankaccountViewHolder(
            CardBankaccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BankaccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BankaccountViewHolder(
        private var binding: CardBankaccountBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bankaccountModel: BankaccountModel) {

            if (bankaccountModel.iban.contains("ABNA")) {
                binding.imageBankcard.setImageResource(R.drawable.abn_amro)
            } else if (bankaccountModel.iban.contains("RABO")) {
                binding.imageBankcard.setImageResource(R.drawable.rabobank)
            } else if (bankaccountModel.iban.contains("INGB")) {
                binding.imageBankcard.setImageResource(R.drawable.ing)
            } else if (bankaccountModel.iban.contains("BUNQ")) {
                binding.imageBankcard.setImageResource(R.drawable.bunq)
            } else if (bankaccountModel.iban.contains("KNAB")) {
                binding.imageBankcard.setImageResource(R.drawable.knab)
            } else if (bankaccountModel.iban.contains("SNSB")) {
                binding.imageBankcard.setImageResource(R.drawable.sns)
            } else {
                binding.imageBankcard.setImageResource(R.drawable.unknown_bank)
            }
            val number = bankaccountModel.iban
            val mask = number.replace("\\w(?=\\w{4})".toRegex(), "*")

            binding.tvIban.text = mask
            binding.tvAccountHolder.text = bankaccountModel.accountHolder
        }
    }
}


