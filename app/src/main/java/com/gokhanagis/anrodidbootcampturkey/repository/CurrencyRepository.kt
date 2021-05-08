package com.gokhanagis.anrodidbootcampturkey.repository

import com.gokhanagis.anrodidbootcampturkey.api.RetrofitIntance
import com.gokhanagis.anrodidbootcampturkey.model.*
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.EUR_GBP
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.EUR_TRY
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.EUR_USD
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.GBP_EUR
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.GBP_TRY
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.GBP_USD
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.TRY_EUR
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.TRY_GBP
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.TRY_USD
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.USD_EUR
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.USD_GBP
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.USD_TRY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

class CurrencyRepository {

    suspend fun getCurrencyRate(q : String, compact : String, apiKey : String): Response<CurrencyResponce> {
        return RetrofitIntance.api.getCurrencyRate(q, compact, apiKey)
    }
}
