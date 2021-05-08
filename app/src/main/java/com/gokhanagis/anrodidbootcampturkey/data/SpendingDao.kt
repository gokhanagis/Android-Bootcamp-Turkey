package com.gokhanagis.anrodidbootcampturkey.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gokhanagis.anrodidbootcampturkey.model.Spending

@Dao
interface SpendingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSpending(spending : Spending)

    @Update()
    suspend fun updateUser(spending : Spending)

    @Delete()
    suspend fun deleteSpending(spending : Spending)

    @Query("DELETE FROM spending_table")
    suspend fun deleteAllSpending()

    @Query("SELECT * FROM  spending_table ORDER BY id ASC")
    fun readALLData(): LiveData<List<Spending>>
}