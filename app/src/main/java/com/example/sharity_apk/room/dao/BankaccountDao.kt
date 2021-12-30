package com.example.sharity_apk.room.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sharity_apk.room.model.BankaccountModel
import kotlinx.coroutines.flow.Flow


@Dao
interface BankaccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: BankaccountModel)


    @Query("SELECT * FROM bankaccount ORDER BY id ASC")
    fun getAllBankaccounts(): Flow<List<BankaccountModel>>

}