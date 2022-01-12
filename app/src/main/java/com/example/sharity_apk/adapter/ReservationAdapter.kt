package com.example.sharity_apk.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.GetAllReservations
import com.example.sharity_apk.R
import com.example.sharity_apk.model.ReservationModel
import com.example.sharity_apk.service.CarApiService
import com.example.sharity_apk.service.ServiceGenerator
import com.example.sharity_apk.viewmodel.ReservationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ReservationAdapter(
    private val reservationList: MutableList<ReservationModel>,
    private val listener: OnReservationClickListener
):
    RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {

        val currentReservation = reservationList[position]

        CoroutineScope(Dispatchers.Main).launch {

            val serviceGenerator = ServiceGenerator.buildService(CarApiService::class.java)
            val car = serviceGenerator.getCar(currentReservation.licensePlate)

            when (val encodedString = serviceGenerator.getCarImage(car.licensePlate.toString()).image) {
                "1" -> holder.ivCar.setImageResource(R.drawable.volvo_xc90)
                "2" -> holder.ivCar.setImageResource(R.drawable.landrover_defender)
                "3" -> holder.ivCar.setImageResource(R.drawable.tesla_3)
                "4" -> holder.ivCar.setImageResource(R.drawable.ford_mustang_convertible)
                "5" -> holder.ivCar.setImageResource(R.drawable.cupra_leon)
                "6" -> holder.ivCar.setImageResource(R.drawable.mercedes_r350_amg)
                "7" -> holder.ivCar.setImageResource(R.drawable.ferrari_testarossa)
                "8" -> holder.ivCar.setImageResource(R.drawable.opel_vectra)
                "9" -> holder.ivCar.setImageResource(R.drawable.toyota_mirai)
                else -> {
                    val imageCar = decodeImageString(encodedString)
                    holder.ivCar.setImageBitmap(imageCar)
                }
            }

            holder.tvReservationNumber.setText(R.string.reservationnumber)
            holder.tvReservationNumberDb.text = currentReservation.reservationNumber.toString()
            holder.tvStartDate.setText(R.string.start_date)
            holder.tvStartDateDb.text = currentReservation.startDate.toString()
            holder.tvEndDate.setText(R.string.end_date)
            holder.tvEndDateDb.text = currentReservation.endDate.toString()
            holder.tvRent.setText(R.string.total_price)
            holder.tvRentDb.text = "€ ${"%.2f".format(currentReservation.rent)}".also { holder.tvRentDb.text = it }
        }
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }

    inner class ReservationViewHolder(itemView: View): RecyclerView.ViewHolder (itemView),
        View.OnClickListener {

        val ivCar: ImageView = itemView.findViewById(R.id.ivCar)
        val tvReservationNumber: TextView = itemView.findViewById(R.id.tv_reservation_number)
        val tvReservationNumberDb: TextView = itemView.findViewById(R.id.tv_reservation_number_db)
        val tvStartDate: TextView = itemView.findViewById(R.id.tv_startDate)
        val tvStartDateDb: TextView = itemView.findViewById(R.id.tv_startdate_db)
        val tvEndDate: TextView = itemView.findViewById(R.id.tv_endDate)
        val tvEndDateDb: TextView = itemView.findViewById(R.id.tv_endDate_db)
        val tvRent: TextView = itemView.findViewById(R.id.tv_rentprice)
        val tvRentDb: TextView = itemView.findViewById(R.id.tv_rentprice_db)

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

    private fun decodeImageString(encodedString: String): Bitmap {
        val imageBytes = Base64.getDecoder().decode(encodedString)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    interface OnReservationClickListener {
        fun onItemClick(position: Int)
    }
}



