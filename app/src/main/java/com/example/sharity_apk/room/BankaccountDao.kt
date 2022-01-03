package com.example.sharity_apk.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sharity_apk.model.BankaccountModel
import kotlinx.coroutines.flow.Flow


@Dao
interface BankaccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: BankaccountModel)

    @Query("SELECT * FROM bankaccount WHERE customer_number = :customerNumber ORDER BY id ASC")
    fun getAllBankaccounts(customerNumber: Long): Flow<List<BankaccountModel>>

    @Query("SELECT * FROM bankaccount WHERE id = :id")
    suspend fun getBankaccount(id: Int): BankaccountModel

    @Query("DELETE FROM bankaccount WHERE id = :id")
    suspend fun deleteBankaccount(id: Int)

    @Query("UPDATE bankaccount SET iban = :iban, account_holder = :account_holder WHERE id = :id")
    suspend fun updateBankaccount(id: Int, iban: String, account_holder: String)
}