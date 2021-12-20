package com.example.sharity_apk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.R
import com.example.sharity_apk.model.CarModel

class CarAdapter(
    private val carList: MutableList<CarModel>,
    private val listener: OnCarClickListener

):
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    //  Initialize the data using the List found in data/DataSource
//    val dataset: List<CarModel> = DataSource.cars


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.car_card, parent, false)

        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {

        val currentCar = carList[position]

////        if (currentBankaccount.iban?.contains("ABNA") == true) {
////            holder.ivBankCard.setImageResource(R.drawable.abn_amro)
////        } else if (currentBankaccount.iban?.contains("RABO") == true) {
////            holder.ivBankCard.setImageResource(R.drawable.rabobank)
////        } else if (currentBankaccount.iban?.contains("INGB") == true) {
////            holder.ivBankCard.setImageResource(R.drawable.ing)
////        } else if (currentBankaccount.iban?.contains("BUNQ") == true) {
////            holder.ivBankCard.setImageResource(R.drawable.bunq)
////        } else if (currentBankaccount.iban?.contains("KNAB") == true) {
////            holder.ivBankCard.setImageResource(R.drawable.knab)
////        } else if (currentBankaccount.iban?.contains("SNSB") == true) {
////            holder.ivBankCard.setImageResource(R.drawable.sns)
////        } else {
////            holder.ivBankCard.setImageResource(R.drawable.unknown_bank)
////        }



        holder.ivCar.setImageResource(R.drawable.ferrari_testarossa)
        holder.tvMake.text = currentCar.make
        holder.tvModel.text = currentCar.model
        holder.tvPrice.text = currentCar.pricePerDay
        holder.tvLocation.text = "Breda"
//        holder.tvCarType.text = currentCar.carType


    }



    override fun getItemCount(): Int {
//        return dataset.size
        return 5
    }


    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivCar: ImageView = itemView?.findViewById(R.id.ivCar)
        val tvMake: TextView = itemView?.findViewById(R.id.tvMake)
        val tvModel: TextView = itemView?.findViewById(R.id.tvModel)
        val tvPrice: TextView = itemView?.findViewById(R.id.tvPrice)
        val tvLocation: TextView = itemView?.findViewById(R.id.tvLocation)
        val tvCarType: TextView = itemView?.findViewById(R.id.tvCarType)

//        fun bindView(carModel: CarModel) {
//            ivCar.setImageResource(R.drawable.ferrari_testarossa)
//            tvModel.text = carModel.model
//            tvMake.text = carModel.make
//            tvPrice.text = carModel.pricePerDay
//            //todo: get the addres of custommer who rents the car
//            tvLocation.text = "Breda"
//            tvCarType.text = carModel.carType
//
//        }
    }

    interface OnCarClickListener {
        fun onItemClick(position: Int)
    }

}




