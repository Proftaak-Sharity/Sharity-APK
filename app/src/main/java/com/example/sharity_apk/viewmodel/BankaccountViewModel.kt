package com.example.sharity_apk.viewmodel

import androidx.lifecycle.*
import com.example.sharity_apk.room.BankaccountDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.sharity_apk.model.Bankaccount
import java.lang.IllegalStateException

class BankaccountViewModel(private val bankaccountDao: BankaccountDao): ViewModel() {

    fun getAllBankaccounts(customerNumber: Long): Flow<List<Bankaccount>> = bankaccountDao.getAllBankaccounts(customerNumber)

    fun addNewItem(customerNumber: Long, iban: String, accountHolder: String) {
        val newBankaccount = getNewItemEntry(customerNumber, iban, accountHolder)
        insertItem(newBankaccount)
    }

    private fun getNewItemEntry(customerNumber: Long, iban: String, accountHolder: String): Bankaccount {
        return Bankaccount(
            customerNumber = customerNumber,
            iban = iban,
            accountHolder = accountHolder
        )
    }

    private fun insertItem(bankaccount: Bankaccount) {
        viewModelScope.launch {
            bankaccountDao.insert(bankaccount)
        }
    }

    suspend fun getBankaccount(id: Int): Bankaccount = bankaccountDao.getBankaccount(id)

    suspend fun deleteBankaccount(id: Int) = bankaccountDao.deleteBankaccount(id)

    suspend fun updateBankaccount(id: Int, iban: String, account_holder: String) = bankaccountDao.updateBankaccount(id, iban, account_holder)

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