package com.example.sharity_apk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharity_apk.databinding.LoginBinding
import com.example.sharity_apk.model.Customer
import com.example.sharity_apk.service.CustomerAdapter
import com.example.sharity_apk.service.CustomerApiService
import com.example.sharity_apk.service.ServiceGenerator
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : Fragment() {

    private var _binding: LoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//      Button bindings:
        binding.buttonLogin.setOnClickListener {

            val emailInput = binding.loginEmailText.text
            val passwordInput = binding.loginPasswordText.text

            val serviceGenerator = ServiceGenerator.buildService(CustomerApiService::class.java)
            viewLifecycleOwner.lifecycleScope.launch {
                val customerNumber =
                    serviceGenerator.getUser(emailInput.toString(), passwordInput.toString())
                Toast.makeText(requireContext(), "$customerNumber", Toast.LENGTH_SHORT).show()
            }

//            val callback = object : Callback<Long> {
//                override fun onResponse(call: Call<Long>, response: Response<Long>) {
////                    if (response.isSuccessful) {
//                      Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
////                    }
//                }
//
//                override fun onFailure(call: Call<Long>, t: Throwable) {
//                    t.printStackTrace()
//                    Log.e("error", t.message.toString())
//                }
//
//            }
//
//            customerNumberCall.enqueue(callback)
//
//
//            if (emailInput.isNullOrEmpty() || passwordInput.isNullOrEmpty()) {
//                Toast.makeText(
//                    this.requireContext(),
//                    "no customer number or password found",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else if (!emailInput.isNullOrEmpty()) {
////                Toast.makeText(
////                    this.requireContext(),
////                    "$customerNumberCall",
////                    Toast.LENGTH_SHORT
////                ).show()
//            } else {
//                Toast.makeText(this.requireContext(), "Error", Toast.LENGTH_SHORT).show()
//            }
//        }

            binding.signIn.setOnClickListener { findNavController().navigate(R.id.action_LoginFragment_to_CreateCustomer) }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}