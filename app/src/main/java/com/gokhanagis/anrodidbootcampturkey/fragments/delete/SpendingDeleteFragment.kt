package com.gokhanagis.anrodidbootcampturkey.fragments.delete

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gokhanagis.anrodidbootcampturkey.R
import com.gokhanagis.anrodidbootcampturkey.viewmodel.SpendingViewModel
import kotlinx.android.synthetic.main.fragment_spending_delete.view.*

class SpendingDeleteFragment : Fragment() {

    private val args  by navArgs<SpendingDeleteFragmentArgs>()
    private lateinit var mSpendingModelView : SpendingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_spending_delete, container, false)

        mSpendingModelView = ViewModelProvider(this).get(SpendingViewModel::class.java)

        view.spendingName_delete_text.setText(args.currentSpending.spendingName)
        view.spendingAmount_delete_text.setText(args.currentSpending.amount.toString()+ " "+ getCurrencySymbol(args.currentSpending.amountType))
        if(args.currentSpending.spendingType.equals("Invoice")){
            view.imageView_delete.setImageResource(R.drawable.ic_invoice_icon)
        }else if(args.currentSpending.spendingType.equals("Rent")){
            view.imageView_delete.setImageResource(R.drawable.ic_rent_icon)
        }else if(args.currentSpending.spendingType.equals("Other")){
            view.imageView_delete.setImageResource(R.drawable.ic_other_icon)
        }

        view.button_delete.setOnClickListener {
            deleteSpending()
        }
        return  view
    }

    private fun deleteSpending() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mSpendingModelView.deleteSpending(args.currentSpending)
            Toast.makeText(requireContext(),"Successfully Deleted: ${args.currentSpending.spendingName}", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_spendingDeleteFragment_to_spendingListFragment)
        }
        builder.setNegativeButton("No"){_, _ ->
        }
        builder.setTitle("Delete ${args.currentSpending.spendingName}?")
        builder.setMessage("Are you sure you want  to delete ${args.currentSpending.spendingName}?")
        builder.create().show()
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