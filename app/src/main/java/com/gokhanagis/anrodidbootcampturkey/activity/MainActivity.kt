 package com.gokhanagis.anrodidbootcampturkey.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.gokhanagis.anrodidbootcampturkey.R
import com.gokhanagis.anrodidbootcampturkey.adapter.OnBoardingViewPagerAdapter
import com.gokhanagis.anrodidbootcampturkey.model.OnBoardingData
import com.google.android.material.tabs.TabLayout

 class MainActivity : AppCompatActivity() {

     var onBoardingViewPagerAdapter : OnBoardingViewPagerAdapter? = null
     var tabLayout : TabLayout? = null
     var onBoardingViewPager : ViewPager? = null
     var next : TextView? = null
     var skip : TextView? = null
     var position = 0
     var sharedPreferences : SharedPreferences? = null

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         requestWindowFeature(Window.FEATURE_NO_TITLE)
         this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
         supportActionBar!!.hide()
         setContentView(R.layout.activity_main)

         if(restorePrefData()){
             val i = Intent(applicationContext, HomeActivity::class.java)
             startActivity(i)
             finish()
         }
         tabLayout = findViewById(R.id.tab_indicator)
         next = findViewById(R.id.nextButton)
         skip = findViewById(R.id.skipButton)

         val onBoardingData : MutableList<OnBoardingData> = ArrayList()
         onBoardingData.add(OnBoardingData(title="Stop Collecting Invoices" , desc ="You no longer need to collect bills.", imageUrl =  R.drawable.img1))
         onBoardingData.add(OnBoardingData(title="Difficult Calculations" , desc ="We take care of the calculations for you", imageUrl = R.drawable.img2))
         onBoardingData.add(OnBoardingData(title="Protect Your Money" , desc ="Save money by limiting your spending", imageUrl = R.drawable.img3))
         setOnboardingViewPagerAdapter(onBoardingData)

         position = onBoardingViewPager!!.currentItem

         next?.setOnClickListener {
             if(position < onBoardingData.size){
                 position++
                 onBoardingViewPager!!.currentItem = position
             }
             if(position == onBoardingData.size){
                 savePrefData()
                 val i = Intent(applicationContext, HomeActivity::class.java)
                 startActivity(i)
             }
         }

         skip?.setOnClickListener {
                 savePrefData()
                 val i = Intent(applicationContext, HomeActivity::class.java)
                 startActivity(i)
         }

         tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
             override fun onTabReselected(tab: TabLayout.Tab?) {
             }

             override fun onTabUnselected(tab: TabLayout.Tab?) {
             }

             override fun onTabSelected(tab: TabLayout.Tab?) {
                 position = tab!!.position
                 if(tab.position == onBoardingData.size - 1){
                     next!!.text = "Get Startted"
                     skip!!.isClickable=false
                     skip!!.visibility= View.INVISIBLE
                 }else{
                     next!!.text = "Next"
                     skip!!.isClickable=true
                     skip!!.visibility= View.VISIBLE
                 }
             }
         })
     }

     private fun setOnboardingViewPagerAdapter(onBoardingData: List<OnBoardingData>) {

         onBoardingViewPager = findViewById(R.id.screenViewPager)
         onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(context = this,
                 onBoardingDataLİst = onBoardingData
         )
         if(onBoardingViewPagerAdapter != null){
             onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
         }
         tabLayout?.setupWithViewPager(onBoardingViewPager)
     }

     private fun savePrefData(){
         sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
         val editor = sharedPreferences!!.edit()
         editor.putBoolean("isFirstTimeRun", true)
         editor.apply()

     }

     private fun restorePrefData (): Boolean {
         sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
         return sharedPreferences!!.getBoolean("isFirstTimeRun" ,false)


     }
 }