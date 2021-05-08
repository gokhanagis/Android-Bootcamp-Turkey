package com.gokhanagis.anrodidbootcampturkey.model

import com.google.gson.annotations.SerializedName

class CurrencyResponce {

        @SerializedName("USD_EUR")
        var USD_EUR: Double = 1.0

        @SerializedName("USD_GBP")
        var USD_GBP: Double = 1.0

        @SerializedName("USD_TRY")
        var USD_TRY: Double = 1.0

        @SerializedName("EUR_USD")
        var EUR_USD: Double = 1.0

        @SerializedName("EUR_GBP")
        var EUR_GBP: Double = 1.0

        @SerializedName("EUR_TRY")
        var EUR_TRY: Double = 1.0

        @SerializedName("GBP_USD")
        var GBP_USD: Double = 1.0

        @SerializedName("GBP_EUR")
        var GBP_EUR: Double = 1.0

        @SerializedName("GBP_TRY")
        var GBP_TRY: Double = 1.0

        @SerializedName("TRY_USD")
        var TRY_USD: Double = 1.0

        @SerializedName("TRY_EUR")
        var TRY_EUR: Double = 1.0

        @SerializedName("TRY_GBP")
        var TRY_GBP: Double = 1.0
}