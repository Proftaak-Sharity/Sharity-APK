package com.example.sharity_apk.data.bankaccount

import androidx.lifecycle.*
import com.example.sharity_apk.room.dao.BankaccountDao
import com.example.sharity_apk.room.model.BankaccountModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class BankaccountViewModel(private val bankaccountDao: BankaccountDao): ViewModel() {

    fun getAllBankaccounts(): Flow<List<BankaccountModel>> = bankaccountDao.getAllBankaccounts()

    fun isEntryValid(iban: String, accountHolder: String): Boolean {
        if (iban.isBlank() || accountHolder.isBlank()) {
            return false
        }
        return true
    }

    fun addNewItem(iban: String, accountHolder: String) {
        val newBankaccount = getNewItemEntry(iban, accountHolder)
        insertItem(newBankaccount)
    }

    private fun getNewItemEntry(iban: String, accountHolder: String): BankaccountModel {
        return BankaccountModel(
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