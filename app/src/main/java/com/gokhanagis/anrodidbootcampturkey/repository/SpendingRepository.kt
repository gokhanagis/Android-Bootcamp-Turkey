package com.gokhanagis.anrodidbootcampturkey.repository

import androidx.lifecycle.LiveData
import com.gokhanagis.anrodidbootcampturkey.data.SpendingDao
import com.gokhanagis.anrodidbootcampturkey.model.Spending

class SpendingRepository (private val spenadingDao: SpendingDao) {

    val readAllData : LiveData<List<Spending>> = spenadingDao.readALLData()

    suspend fun  addSpending(spending: Spending){
        spenadingDao.addSpending(spending)
    }

    suspend fun updateSpending(spending: Spending){
        spenadingDao.updateUser(spending)
    }

    suspend fun deleteSpending(spending: Spending){
        spenadingDao.deleteSpending(spending)
    }

    suspend fun deleteAllSpending(){
        spenadingDao.deleteAllSpending()
    }
}