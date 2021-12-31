package com.example.sharity_apk.viewmodel

import androidx.lifecycle.*
import com.example.sharity_apk.room.dao.BankaccountDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.sharity_apk.room.model.BankaccountModel
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.IllegalStateException

class BankaccountViewModel(private val bankaccountDao: BankaccountDao): ViewModel() {

    fun getAllBankaccounts(customerNumber: Long): Flow<List<BankaccountModel>> = bankaccountDao.getAllBankaccounts(customerNumber)

    fun bankaccountsList(customerNumber: Long): MutableList<BankaccountModel> = bankaccountDao.bankaccountsList(customerNumber)

    fun isEntryValid(iban: String, accountHolder: String): Boolean {
        if (iban.isBlank() || accountHolder.isBlank()) {
            return false
        }
        return true
    }

    fun addNewItem(customerNumber: Long, iban: String, accountHolder: String) {
        val newBankaccount = getNewItemEntry(customerNumber, iban, accountHolder)
        insertItem(newBankaccount)
    }

    private fun getNewItemEntry(customerNumber: Long, iban: String, accountHolder: String): BankaccountModel {
        return BankaccountModel(
            customerNumber = customerNumber,
            iban = iban,
            accountHolder = accountHolder
        )
    }

    private fun insertItem(bankaccount: BankaccountModel) {
        viewModelScope.launch {
            bankaccountDao.insert(bankaccount)
        }
    }


}

class BankaccountViewModelFactory(private val bankaccountDao: BankaccountDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BankaccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BankaccountViewModel(bankaccountDao) as T
        }
        throw IllegalStateException("Unknown ViewModel Class")
    }
}