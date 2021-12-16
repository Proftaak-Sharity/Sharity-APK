package com.example.sharity_apk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharity_apk.databinding.UpdateCustomerBinding
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.CustomerAdapter
import com.example.sharity_apk.model.Customer
import com.example.sharity_apk.service.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateCustomer: Fragment() {

    private var _binding: UpdateCustomerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UpdateCustomerBinding.inflate(inflater, container, false)

        val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
        val call = serviceGenerator.getCustomers()

        call.enqueue(object : Callback<MutableList<Customer>> {
            override fun onResponse(call: Call<MutableList<Customer>>, response: Response<MutableList<Customer>>) {
                if (response.isSuccessful) {
                    binding.myRecyclerView.apply {
                        layoutManager = LinearLayoutManager(this@UpdateCustomer.requireContext())
                        adapter = CustomerAdapter(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Customer>>, t: Throwable) {
                t.printStackTrace()
                Log.e("error", t.message.toString())
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}


