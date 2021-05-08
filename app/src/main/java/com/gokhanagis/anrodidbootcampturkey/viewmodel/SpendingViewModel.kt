package com.gokhanagis.anrodidbootcampturkey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gokhanagis.anrodidbootcampturkey.data.SpendingDatabase
import com.gokhanagis.anrodidbootcampturkey.model.Spending
import com.gokhanagis.anrodidbootcampturkey.repository.SpendingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpendingViewModel  (application: Application) : AndroidViewModel(application) {
    val readAllData : LiveData<List<Spending>>
    private val repository : SpendingRepository

    init {
        val spendingDao = SpendingDatabase.getDatabase(
                application
        ).spendingDao()
        repository =
                SpendingRepository(
                        spendingDao
                )
        readAllData = repository.readAllData
    }

    fun addSpending(spending: Spending){
        viewModelScope.launch (Dispatchers.IO){
            repository.addSpending(spending)
        }

    }

    fun updateSpending(spending: Spending){
        viewModelScope.launch { Dispatchers.IO
            repository.updateSpending(spending)
        }
    }

    fun deleteSpending(spending: Spending){
        viewModelScope.launch { Dispatchers.IO
            repository.deleteSpending(spending)
        }
    }

    fun deleteAllSpending(){
        viewModelScope.launch { Dispatchers.IO
            repository.deleteAllSpending()
        }
    }

}