package com.gokhanagis.anrodidbootcampturkey.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gokhanagis.anrodidbootcampturkey.R
import com.gokhanagis.anrodidbootcampturkey.model.Spending
import kotlinx.android.synthetic.main.spending_custom_layout.view.*
import kotlinx.android.synthetic.main.user_information_layout.view.*

class SpendingListAdapter : RecyclerView.Adapter<SpendingListAdapter.MyViewHolder>() {

    private var spendingsList = emptyList<Spending>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.spending_custom_layout, parent,false))
    }

    override fun getItemCount(): Int {
        return  spendingsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = spendingsList[position]
        holder.itemView.text_spending_name.text = currentItem.spendingName
        holder.itemView.text_spending_amount.text = currentItem.amount.toString() +"  "+ getCurrencySymbol(currentItem.amountType)
        if(currentItem.spendingType.equals("Invoice")){
            holder.itemView.image_icon.setImageResource(R.drawable.ic_invoice_icon)
        }else if(currentItem.spendingType.equals("Rent")){
            holder.itemView.image_icon.setImageResource(R.drawable.ic_rent_icon)
        }else if(currentItem.spendingType.equals("Other")){
            holder.itemView.image_icon.setImageResource(R.drawable.ic_other_icon)
        }

        holder.itemView.rowLayout.setOnClickListener {
            val action = SpendingListFragmentDirections.actionSpendingListFragmentToSpendingDeleteFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }
    fun setData(spending: List<Spending>){
        this.spendingsList = spending
        notifyDataSetChanged()
    }

    fun getCurrencySymbol(amountType:String):String{

        lateinit var amountTypeSymbol:String

        if(amountType.equals("TL")){
            amountTypeSymbol ="TL"
        }else if(amountType.equals("Dolar")){
            amountTypeSymbol ="$"
        }
        else if(amountType.equals("Euro")){
            amountTypeSymbol ="€"
        }else if(amountType.equals("Sterlin")){
            amountTypeSymbol ="£"
        }
        return amountTypeSymbol
    }



}