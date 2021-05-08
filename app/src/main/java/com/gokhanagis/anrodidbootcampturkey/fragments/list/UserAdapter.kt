package com.gokhanagis.anrodidbootcampturkey.fragments.list

import android.icu.util.CurrencyAmount
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gokhanagis.anrodidbootcampturkey.R
import com.gokhanagis.anrodidbootcampturkey.fragments.update.UpdateUserFragmentDirections
import com.gokhanagis.anrodidbootcampturkey.model.User
import kotlinx.android.synthetic.main.spending_custom_layout.view.*
import kotlinx.android.synthetic.main.user_information_layout.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()
    private var totalAmount : Double = 0.0
    private var totalAmountType : String = "TL"

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_information_layout, parent,false))
    }

    override fun getItemCount(): Int {
        return  userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.user_name_txt.text = currentItem.nameAndLastName
        holder.itemView.user_total_amount_text.text = totalAmount.toString() +" " + totalAmountType
        if (currentItem.gender.equals("Man")) {
            holder.itemView.user_name_txt.text = "Mr. " + holder.itemView.user_name_txt.text
        } else if (currentItem.gender.equals("Woman")) {
            holder.itemView.user_name_txt.text = "Ms. " + holder.itemView.user_name_txt.text
        } else if (currentItem.gender.equals("Other")) {
            holder.itemView.user_name_txt.text = "Mr. Or Ms. " + holder.itemView.user_name_txt.text
        }
        holder.itemView.welcomeConstraintLayout.setOnClickListener {
            val action = SpendingListFragmentDirections.actionSpendingListFragmentToUpdateUserFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user : List<User>, totalAmount: Double, totalAmountType : String){
        this.userList = user
        this.totalAmount = totalAmount
        this.totalAmountType = totalAmountType
        notifyDataSetChanged()
    }
}
