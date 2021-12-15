package com.example.sharity_apk.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.avd.demoretrofit.CustomerApi
import com.avans.avd.demoretrofit.CustomerApiService
import com.example.sharity_apk.model.Customer
import kotlinx.coroutines.launch


private const val TAG = "CustomerViewModel"

class CustomerViewModel : ViewModel() {

    private val _customerResponse: MutableLiveData<String> = MutableLiveData()

    val customerResponse: LiveData<String> // todo: voor demo String, later List<TodoItem>
        get() = _customerResponse

    init {
        getCustomers()
    }


    fun getCustomers() {
        viewModelScope.launch {
            try {
                Log.i(TAG, "getTodoItems: launch started")
                _customerResponse.value = CustomerApi.retrofitService.getCustomers()
            } catch (e: Exception) {
                _customerResponse.value = e.message.toString()
            }
        }
    }


//    fun deleteCustomer(customerNumber: Long) {
//        viewModelScope.launch {
//            try {
//                CustomerApi.retrofitService.deleteCustomer(customerNumber)
//                Log.i(TAG, "deleteTodoItem: ${customerNumber} deleted")
//                _customerResponse.value = "deleteTodoItem: $customerNumber deleted"
//            } catch (e: Exception) {
//                _customerResponse.value = e.message.toString()
//            }
//        }
//    }
//
//    fun addCustomer(customer: CustomerModel) {
//        viewModelScope.launch {
//            try {
//                CustomerApi.retrofitService.addCustomer(customer)
//                Log.i(TAG, "postTodoItem: $customer posted")
//                _customerResponse.value = "postTodoItem: $customer posted"
//            } catch (e: Exception) {
//                _customerResponse.value = e.message.toString()
//            }
//        }
//    }

}