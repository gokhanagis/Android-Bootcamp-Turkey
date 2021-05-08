package com.gokhanagis.anrodidbootcampturkey.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gokhanagis.anrodidbootcampturkey.model.Spending

@Database(entities = [Spending::class], version =1, exportSchema = false)
abstract class SpendingDatabase : RoomDatabase(){

    abstract fun spendingDao(): SpendingDao

    companion object{

        @Volatile
        private var INSTANCE: SpendingDatabase? =  null

        fun getDatabase(context: Context): SpendingDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return  tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        SpendingDatabase::class.java,
                        "spending_table"
                ).build()
                INSTANCE =  instance
                return instance
            }
        }
    }
}