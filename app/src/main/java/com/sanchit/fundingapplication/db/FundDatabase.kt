package com.sanchit.fundingapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sanchit.fundingapplication.models.Record

@Database(
    entities = [Record::class],
    version = 1
)
abstract class FundDatabase :RoomDatabase() {

   abstract fun getFundsDao() : FundDao

   companion object{
       @Volatile
       private var instance : FundDatabase? = null
       private val LOCK = Any()

       operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
           instance ?:createDatabase(context).also{
               instance = it
           }
       }

       private fun createDatabase(context: Context) =
           Room.databaseBuilder(
               context.applicationContext,
               FundDatabase::class.java,
               "Fund_db.db"
           ).build()
   }
}