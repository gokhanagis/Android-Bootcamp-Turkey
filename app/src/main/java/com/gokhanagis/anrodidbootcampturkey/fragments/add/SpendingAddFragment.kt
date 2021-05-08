package com.gokhanagis.anrodidbootcampturkey.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gokhanagis.anrodidbootcampturkey.R
import com.gokhanagis.anrodidbootcampturkey.model.Spending
import com.gokhanagis.anrodidbootcampturkey.viewmodel.SpendingViewModel
import kotlinx.android.synthetic.main.fragment_spending_add.*
import kotlinx.android.synthetic.main.fragment_spending_add.view.*


class SpendingAddFragment : Fragment() {

    private lateinit var mSpendingViewModel: SpendingViewModel
    lateinit var radioSpendingType: RadioButton
    lateinit var radioCurrency: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_spending_add, container, false)

        mSpendingViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)
        view.add_btn.setOnClickListener {

            var idSpendingType: Int = view.radioGroupSpendingType.checkedRadioButtonId
            var idCurrency: Int = view.radioGroupCurrency.checkedRadioButtonId
            if (idSpendingType!=-1 && idCurrency!=-1){
                radioSpendingType = view.findViewById(idSpendingType)
                radioCurrency = view.findViewById(idCurrency)
                if(!radioSpendingType?.text.isEmpty() && !radioCurrency?.text.isEmpty()){
                    insertDataToDatabse()
                }
            }else{
                Toast.makeText(requireContext()," Please fill out all fields!", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    private fun insertDataToDatabse() {

        val SpendingName = spendingName_text.text.toString()
        val spendingAmount  = spendingAmount_text.text.toString()
        val gender =  radioSpendingType.text.toString()
        val amountType = radioCurrency.text.toString()

        if(inputCheck(SpendingName, spendingAmount, gender, amountType)){

            val spending = Spending(
                    0,
                    SpendingName,
                    spendingAmount.toDouble(),
                    gender,
                    amountType
            )
            mSpendingViewModel.addSpending(spending)
            Toast.makeText(requireContext(),"Successfully added your spending!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_spendingAddFragment2_to_spendingListFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(SpendingName: String, spendingAmount:String, gender: String, amountType:String): Boolean{
        return !(TextUtils.isEmpty(SpendingName) && TextUtils.isEmpty(gender) && TextUtils.isEmpty(amountType) && TextUtils.isEmpty(spendingAmount) )
    }
}