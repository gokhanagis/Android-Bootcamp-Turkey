package com.gokhanagis.anrodidbootcampturkey.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gokhanagis.anrodidbootcampturkey.repository.CurrencyRepository

class CurrencyViewModelFactory (private val repository: CurrencyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrencyViewModel(repository) as T
    }


}