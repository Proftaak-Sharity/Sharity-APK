package com.example.sharity_apk

import android.os.Bundle
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat.apply
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.adapter.ReservationItemAdapter
import com.example.sharity_apk.data.Datasource
import com.example.sharity_apk.databinding.GetAllReservationsBinding

class GetAllReservations  : Fragment() {
    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    private var _binding: GetAllReservationsBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//    private val myDataset = Datasource().loadReservations()
//
//    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ReservationList)
//    recyclerView.adapter = ReservationItemAdapter(this, myDataset)
//    recyclerView.setHasFixedSize(true)
//
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ReservationItemAdapter.ReservationItemViewHolder>? =
        null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.get_all_reservations, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    }
