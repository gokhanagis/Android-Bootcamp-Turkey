package com.gokhanagis.anrodidbootcampturkey.model

import android.icu.util.CurrencyAmount
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "spending_table")
class Spending (

        @PrimaryKey(autoGenerate = true)
        val id : Int,
        val spendingName: String,
        var amount: Double,
        val spendingType: String,
        var amountType: String
):Parcelable