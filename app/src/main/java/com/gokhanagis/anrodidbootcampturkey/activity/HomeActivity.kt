package com.gokhanagis.anrodidbootcampturkey.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.gokhanagis.anrodidbootcampturkey.R
import com.gokhanagis.anrodidbootcampturkey.repository.CurrencyRepository
import com.gokhanagis.anrodidbootcampturkey.viewmodel.CurrencyViewModel
import com.gokhanagis.anrodidbootcampturkey.viewmodel.CurrencyViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupActionBarWithNavController(findNavController(R.id.fragment))
       /* var result : String=""
        val repository = CurrencyRepository()
        val viewModelFactory = CurrencyViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrencyViewModel::class.java)
        viewModel.getUsdTry()
        viewModel.myResponceUsdTry.observe(this, Observer { responce ->
            if(responce.isSuccessful){
                result = responce.body()?.USD_TRY.toString()
            }
        })*/

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =  findNavController(R.id.fragment)
        return  navController.navigateUp() || super.onSupportNavigateUp()
    }
}