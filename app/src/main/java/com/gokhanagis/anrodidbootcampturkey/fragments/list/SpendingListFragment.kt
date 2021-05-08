package com.gokhanagis.anrodidbootcampturkey.fragments.list

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokhanagis.anrodidbootcampturkey.R
import com.gokhanagis.anrodidbootcampturkey.adapter.CurrencyAdapter
import com.gokhanagis.anrodidbootcampturkey.model.CurrencyResponce
import com.gokhanagis.anrodidbootcampturkey.model.User
import com.gokhanagis.anrodidbootcampturkey.repository.CurrencyRepository
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.API_KEY
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.COMPACT
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.CURRENCY_RATE
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.EUR_GBP
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.EUR_TRY
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.EUR_USD
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.GBP_EUR
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.GBP_TRY
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.GBP_USD
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.PREFS_FILENAME
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.TRY_EUR
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.TRY_GBP
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.TRY_USD
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.USD_EUR
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.USD_GBP
import com.gokhanagis.anrodidbootcampturkey.util.Constants.Companion.USD_TRY
import com.gokhanagis.anrodidbootcampturkey.viewmodel.CurrencyViewModel
import com.gokhanagis.anrodidbootcampturkey.viewmodel.CurrencyViewModelFactory
import com.gokhanagis.anrodidbootcampturkey.viewmodel.SpendingViewModel
import com.gokhanagis.anrodidbootcampturkey.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_spending_list.view.*
import kotlinx.android.synthetic.main.user_information_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext

class SpendingListFragment : Fragment() {

    private lateinit var mSpendingViewModel: SpendingViewModel
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var viewModel: CurrencyViewModel
    private var totalAmount : Double = 0.0
    private var totalAmountType : String = "TL"
    val listRates: MutableList<String> = mutableListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

         val sharedPreferences = requireContext().getSharedPreferences(PREFS_FILENAME, android.content.Context.MODE_PRIVATE)
         // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_spending_list, container, false)

        val adapter = SpendingListAdapter()
        val recyclerView  = view.recyclerView
        recyclerView.adapter =  adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mSpendingViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)
        mSpendingViewModel.readAllData.observe(viewLifecycleOwner, Observer {spending->
            totalAmount = 0.0
            for (item in spending){
                if(item.amountType.equals("TL")){
                    totalAmount += item.amount
                }else{
                    if(item.amountType.equals("Euro")){
                        currencyConverterCurrencyPair(EUR_TRY, COMPACT , API_KEY)
                    }else if(item.amountType.equals("Dolar")){
                        currencyConverterCurrencyPair(USD_TRY, COMPACT , API_KEY)
                    }
                    else if(item.amountType.equals("Sterlin")){
                        currencyConverterCurrencyPair(GBP_TRY, COMPACT , API_KEY)
                    }
                    Thread.sleep(1_000)
                    var resultShare : Double = sharedPreferences.getString(CURRENCY_RATE,"1")?.toDouble()!!
                    var resultTimesTotal : Double = "%.2f".format(item.amount.times(resultShare)).toDouble()
                    totalAmount = totalAmount.plus(resultTimesTotal)
                    item.amount = resultTimesTotal
                    item.amountType = "TL"
                }
                sharedPreferences.edit().remove(CURRENCY_RATE)

            }
            adapter .setData(spending)
            view.button_tl.setTextColor(Color.parseColor("#FF9800"))
            view.button_dolar.setTextColor(Color.parseColor("#FFFFFFFF"))
            view.button_euro.setTextColor(Color.parseColor("#FFFFFFFF"))
            view.button_sterlin.setTextColor(Color.parseColor("#FFFFFFFF"))
        })

         view.floatingActionButton.setOnClickListener {
             findNavController().navigate(R.id.action_spendingListFragment_to_spendingAddFragment2)
         }

         view.button_dolar.setOnClickListener {
             totalAmount = 0.0
             mSpendingViewModel.readAllData.observe(viewLifecycleOwner, Observer {spending->
                 for (item in spending){
                     if(item.amountType.equals("Dolar")){
                         totalAmount += item.amount
                     }else{
                         if(item.amountType.equals("Euro")){
                             currencyConverterCurrencyPair(EUR_USD, COMPACT , API_KEY)
                         }else if(item.amountType.equals("TL")){
                             GlobalScope.launch(Dispatchers.Main) {
                                 currencyConverterCurrencyPair(TRY_USD, COMPACT , API_KEY)
                             }
                         }
                         else if(item.amountType.equals("Sterlin")){
                             currencyConverterCurrencyPair(GBP_USD, COMPACT , API_KEY)
                         }
                         var resultShare : Double = sharedPreferences.getString(CURRENCY_RATE,"1")?.toDouble()!!
                         var resultTimesTotal : Double = "%.2f".format(item.amount.times(resultShare)).toDouble()
                         totalAmount = totalAmount.plus(resultTimesTotal)
                         item.amount = resultTimesTotal
                         item.amountType = "Dolar"
                     }
                     sharedPreferences.edit().remove(CURRENCY_RATE)
                 }
                 adapter .setData(spending)
             })
             var totalAmountWithSymbol = totalAmount.toString() + " $"
             view.recyclerViewUser.user_total_amount_text.setText(totalAmountWithSymbol)
             view.button_tl.setTextColor(Color.parseColor("#FFFFFFFF"))
             view.button_dolar.setTextColor(Color.parseColor("#FF9800"))
             view.button_euro.setTextColor(Color.parseColor("#FFFFFFFF"))
             view.button_sterlin.setTextColor(Color.parseColor("#FFFFFFFF"))
         }

        view.button_euro.setOnClickListener {
            totalAmount = 0.0
            mSpendingViewModel.readAllData.observe(viewLifecycleOwner, Observer {spending->
                for (item in spending){
                    if(item.amountType.equals("Euro")){
                        totalAmount += item.amount
                    }else{
                        if(item.amountType.equals("Dolar")){
                            currencyConverterCurrencyPair(USD_EUR, COMPACT , API_KEY)
                        }else if(item.amountType.equals("TL")){
                            currencyConverterCurrencyPair(EUR_TRY, COMPACT , API_KEY)
                        }
                        else if(item.amountType.equals("Sterlin")){
                            currencyConverterCurrencyPair(GBP_EUR, COMPACT , API_KEY)
                        }
                        var resultShare : Double = sharedPreferences.getString(CURRENCY_RATE,"1")?.toDouble()!!
                        var resultTimesTotal : Double = "%.2f".format(item.amount.times(resultShare)).toDouble()
                        totalAmount = totalAmount.plus(resultTimesTotal)
                        item.amount = resultTimesTotal
                        item.amountType = "Euro"
                    }
                    sharedPreferences.edit().remove(CURRENCY_RATE)
                }
                adapter .setData(spending)
            })
            var totalAmountWithSymbol = totalAmount.toString() + " €"
            view.recyclerViewUser.user_total_amount_text.setText(totalAmountWithSymbol)
            view.button_tl.setTextColor(Color.parseColor("#FFFFFFFF"))
            view.button_dolar.setTextColor(Color.parseColor("#FFFFFFFF"))
            view.button_euro.setTextColor(Color.parseColor("#FF9800"))
            view.button_sterlin.setTextColor(Color.parseColor("#FFFFFFFF"))
        }

        view.button_sterlin.setOnClickListener {
            totalAmount = 0.0
            mSpendingViewModel.readAllData.observe(viewLifecycleOwner, Observer {spending->
                for (item in spending){
                    if(item.amountType.equals("Sterlin")){
                        totalAmount += item.amount
                    }else{
                        if(item.amountType.equals("Dolar")){
                            currencyConverterCurrencyPair(USD_GBP, COMPACT , API_KEY)
                        }else if(item.amountType.equals("TL")){
                            currencyConverterCurrencyPair(TRY_GBP, COMPACT , API_KEY)
                        }
                        else if(item.amountType.equals("Euro")){
                            currencyConverterCurrencyPair(EUR_GBP, COMPACT , API_KEY)
                        }
                        var resultShare : Double = sharedPreferences.getString(CURRENCY_RATE,"1")?.toDouble()!!
                        var resultTimesTotal : Double = "%.2f".format(item.amount.times(resultShare)).toDouble()
                        totalAmount = totalAmount.plus(resultTimesTotal)
                        item.amount = resultTimesTotal
                        item.amountType = "Sterlin"
                    }
                    sharedPreferences.edit().remove(CURRENCY_RATE)
                }
                adapter .setData(spending)
            })
            var totalAmountWithSymbol = totalAmount.toString() + " TL"
            view.recyclerViewUser.user_total_amount_text.setText(totalAmountWithSymbol)
            view.button_tl.setTextColor(Color.parseColor("#FFFFFFFF"))
            view.button_dolar.setTextColor(Color.parseColor("#FFFFFFFF"))
            view.button_euro.setTextColor(Color.parseColor("#FFFFFFFF"))
            view.button_sterlin.setTextColor(Color.parseColor("#FF9800"))
        }

        view.button_tl.setOnClickListener {
            totalAmount = 0.0
            mSpendingViewModel.readAllData.observe(viewLifecycleOwner, Observer {spending->
                for (item in spending){
                    if(item.amountType.equals("TL")){
                        totalAmount += item.amount
                    }else{
                        if(item.amountType.equals("Dolar")){
                            GlobalScope.launch(Dispatchers.Main) {
                                currencyConverterCurrencyPair(USD_TRY, COMPACT , API_KEY)
                            }

                        }else if(item.amountType.equals("Sterlin")){
                            currencyConverterCurrencyPair(GBP_TRY, COMPACT , API_KEY)
                        }
                        else if(item.amountType.equals("Euro")){
                            currencyConverterCurrencyPair(EUR_TRY, COMPACT , API_KEY)
                        }
                        var resultShare : Double = sharedPreferences.getString(CURRENCY_RATE,"1")?.toDouble()!!
                        var resultTimesTotal : Double = "%.2f".format(item.amount.times(resultShare)).toDouble()
                        totalAmount = totalAmount.plus(resultTimesTotal)
                        item.amount = resultTimesTotal
                        item.amountType = "TL"
                    }
                    sharedPreferences.edit().remove(CURRENCY_RATE)
                }
                adapter .setData(spending)
            })
            var totalAmountWithSymbol = totalAmount.toString() + " TL"
            view.recyclerViewUser.user_total_amount_text.setText(totalAmountWithSymbol)
            view.button_tl.setTextColor(Color.parseColor("#FF9800"))
            view.button_dolar.setTextColor(Color.parseColor("#FFFFFFFF"))
            view.button_euro.setTextColor(Color.parseColor("#FFFFFFFF"))
            view.button_sterlin.setTextColor(Color.parseColor("#FFFFFFFF"))
        }

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val adapterUser = UserAdapter()
        val recyclerViewUser  = view.recyclerViewUser
        recyclerViewUser.adapter =  adapterUser
        recyclerViewUser.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer {user->
            if(user == null || user.isEmpty() || user.size == 0){
                val userDefault = User(1, "Your Name" , "")
                mUserViewModel.addUser(userDefault)
            }
            adapterUser.setData(user,totalAmount, totalAmountType)
        })

        return view
    }


    fun currencyConverterCurrencyPair(q : String, compact : String, apiKey : String) {

        val adapter = CurrencyAdapter()
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_FILENAME, android.content.Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(CURRENCY_RATE)
        val repository = CurrencyRepository()
        val viewModelFactory = CurrencyViewModelFactory(repository)
        sharedPreferences.edit().remove(CURRENCY_RATE)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrencyViewModel::class.java)
        if(q.equals(TRY_USD)){
            viewModel.getCurrencyRateTryUsd(q, compact, apiKey)
            viewModel.myResponceCurrencyRateTryUsd.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.TRY_USD.toString().equals("1.0")) {
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.TRY_USD).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }else if(q.equals(USD_TRY)){
            viewModel.getCurrencyRateUsdTry(q, compact, apiKey)
            viewModel.myResponceCurrencyRateUsdTry.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.USD_TRY.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.USD_TRY).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(EUR_TRY)){
            viewModel.getCurrencyRateEurTry(q, compact, apiKey)
            viewModel.myResponceCurrencyRateEurTry.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.EUR_TRY.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.EUR_TRY).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(TRY_EUR)){
            viewModel.getCurrencyRateTryEur(q, compact, apiKey)
            viewModel.myResponceCurrencyRateTryEur.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.TRY_EUR.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.TRY_EUR).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(GBP_TRY)){
            viewModel.getCurrencyRateGbpTry(q, compact, apiKey)
            viewModel.myResponceCurrencyRateGbpTry.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.GBP_TRY.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.GBP_TRY).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(TRY_GBP)){
            viewModel.getCurrencyRateTryGbp(q, compact, apiKey)
            viewModel.myResponceCurrencyRateTryGbp.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.TRY_GBP.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.TRY_GBP).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(USD_EUR)){
            viewModel.getCurrencyRateUsdEur(q, compact, apiKey)
            viewModel.myResponceCurrencyRateUsdEur.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.USD_EUR.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.USD_EUR).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(EUR_USD)){
            viewModel.getCurrencyRateEurUsd(q, compact, apiKey)
            viewModel.myResponceCurrencyRateEurUsd.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.EUR_USD.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.EUR_USD).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(USD_GBP)){
            viewModel.getCurrencyRateUsdGbp(q, compact, apiKey)
            viewModel.myResponceCurrencyRateUsdGbp.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.USD_GBP.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.USD_GBP).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(GBP_USD)){
            viewModel.getCurrencyRateGbpUsd(q, compact, apiKey)
            viewModel.myResponceCurrencyRateGbpUsd.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.GBP_USD.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.GBP_USD).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(EUR_GBP)){
            viewModel.getCurrencyRateEurGbp(q, compact, apiKey)
            viewModel.myResponceCurrencyRateEurGbp.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.EUR_GBP.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.EUR_GBP).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
        else if(q.equals(GBP_EUR)){
            viewModel.getCurrencyRateGbpEur(q, compact, apiKey)
            viewModel.myResponceCurrencyRateGbpEur.observe(viewLifecycleOwner, Observer { responce ->
                if (responce.isSuccessful) {
                    val currencyResponce : CurrencyResponce = responce.body()!!
                    if(!currencyResponce.GBP_EUR.toString().equals("1.0")){
                        sharedPreferences.edit().putString(CURRENCY_RATE, (currencyResponce?.GBP_EUR).toString()).apply()
                    }
                } else{
                    Toast.makeText(requireContext()," Sorry an Error for converter currency!", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}






