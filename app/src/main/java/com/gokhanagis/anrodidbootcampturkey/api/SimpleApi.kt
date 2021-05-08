package com.gokhanagis.anrodidbootcampturkey.api

import com.gokhanagis.anrodidbootcampturkey.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SimpleApi {

    @GET("api/v7/convert?")
    suspend fun getCurrencyRate(@Query("q") q: String, @Query("compact") compact: String, @Query("apiKey") apiKey: String): Response<CurrencyResponce>

}