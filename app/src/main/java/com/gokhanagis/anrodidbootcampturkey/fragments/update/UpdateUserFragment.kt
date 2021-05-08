package com.gokhanagis.anrodidbootcampturkey.fragments.update

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gokhanagis.anrodidbootcampturkey.R
import com.gokhanagis.anrodidbootcampturkey.fragments.delete.SpendingDeleteFragmentArgs
import com.gokhanagis.anrodidbootcampturkey.model.Spending
import com.gokhanagis.anrodidbootcampturkey.model.User
import com.gokhanagis.anrodidbootcampturkey.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_spending_add.*
import kotlinx.android.synthetic.main.fragment_update_user.*
import kotlinx.android.synthetic.main.fragment_update_user.view.*
import kotlinx.android.synthetic.main.user_information_layout.view.*

class UpdateUserFragment : Fragment() {

    private val args  by navArgs<UpdateUserFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel
    lateinit var radioUserGenderType: RadioButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_update_user, container, false)

        view.userName_text.setText(args.currentUser.nameAndLastName.toString())
        if (args.currentUser.gender.equals("Man")) {
            view.update_page_user_man.isChecked = true
        } else if (args.currentUser.gender.equals("Woman")) {
            view.update_page_user_women.isChecked = true
        } else if (args.currentUser.gender.equals("Other")) {
            view.update_page_user_other.isChecked = true
        }

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.update_user_btn.setOnClickListener {

            var idUserGender: Int = view.radioGroupUsernGender.checkedRadioButtonId
            if (idUserGender!=-1){
                radioUserGenderType = view.findViewById(idUserGender)
            }else{
                Toast.makeText(requireContext()," Please selected your gender!", Toast.LENGTH_LONG).show()
            }
            if(!radioUserGenderType.text.isEmpty()){
                insertOrUpdateDataToDatabse()
            }
        }

        return view;
    }

    private fun insertOrUpdateDataToDatabse() {

        val userName = userName_text.text.toString()
        val gender =  radioUserGenderType.text.toString()

        if(inputCheck(userName,  gender)){

            val user = User(
                    1,
                    userName,
                    gender
            )
            mUserViewModel.updateUser(user)
            Toast.makeText(requireContext(),"Successfully update your name and gender!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateUserFragment_to_spendingListFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(userNAme: String, gender: String, ): Boolean{
        return !(TextUtils.isEmpty(userNAme) && TextUtils.isEmpty(gender) )
    }
}