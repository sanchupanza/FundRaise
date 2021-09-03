package com.sanchit.fundingapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanchit.fundingapplication.models.Record

@Dao
interface FundDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fund: Record): Long

    @Query("SELECT * FROM funds")
    fun getAllFunds(): LiveData<List<Record>>
}