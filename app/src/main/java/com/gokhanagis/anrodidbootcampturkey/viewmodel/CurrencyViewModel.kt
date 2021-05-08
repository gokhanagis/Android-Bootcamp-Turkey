package com.gokhanagis.anrodidbootcampturkey.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gokhanagis.anrodidbootcampturkey.model.*
import com.gokhanagis.anrodidbootcampturkey.repository.CurrencyRepository
import kotlinx.coroutines.*
import retrofit2.Response

class CurrencyViewModel(private val repository: CurrencyRepository): ViewModel() {

    val myResponceCurrencyRateTryUsd : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateTryEur : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateTryGbp : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateUsdTry : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateUsdEur : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateUsdGbp : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateEurTry : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateEurUsd : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateEurGbp : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateGbpTry : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateGbpUsd : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()
    val myResponceCurrencyRateGbpEur : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()


    fun getCurrencyRateTryUsd(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateTryUsd.value = responce
        }
    }
    fun getCurrencyRateTryEur(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateTryEur.value = responce
        }
    }
    fun getCurrencyRateTryGbp(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateTryGbp.value = responce
        }
    }
    fun getCurrencyRateUsdTry(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateUsdTry.value = responce
        }
    }
    fun getCurrencyRateUsdEur(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateUsdEur.value = responce
        }
    }
    fun getCurrencyRateUsdGbp(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateUsdGbp.value = responce
        }
    }
    fun getCurrencyRateEurTry(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateEurTry.value = responce
        }
    }
    fun getCurrencyRateEurUsd(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateEurUsd.value = responce
        }
    }
    fun getCurrencyRateEurGbp(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateEurGbp.value = responce
        }
    }
    fun getCurrencyRateGbpTry(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateGbpTry.value = responce
        }
    }
    fun getCurrencyRateGbpUsd(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateGbpUsd.value = responce
        }
    }
    fun getCurrencyRateGbpEur(q : String, compact : String, apiKey : String){
        viewModelScope.launch {
            val responce = repository.getCurrencyRate(q, compact, apiKey)
            myResponceCurrencyRateGbpEur.value = responce
        }
    }



}