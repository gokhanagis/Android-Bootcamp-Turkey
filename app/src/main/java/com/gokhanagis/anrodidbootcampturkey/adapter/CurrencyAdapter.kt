package com.gokhanagis.anrodidbootcampturkey.adapter

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.gokhanagis.anrodidbootcampturkey.model.CurrencyResponce
import retrofit2.Response

class CurrencyAdapter: RecyclerView.Adapter<CurrencyAdapter.MyViewHolder>() {

    private var currencyList : MutableLiveData<Response<CurrencyResponce>> = MutableLiveData()

    inner class MyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyAdapter.MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun setData(newList: MutableLiveData<Response<CurrencyResponce>>){
        this.currencyList = newList
        notifyDataSetChanged()
    }

}