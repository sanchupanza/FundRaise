package com.sanchit.fundingapplication.repository

import com.sanchit.fundingapplication.api.RetrofitInstance
import com.sanchit.fundingapplication.db.FundDatabase
import com.sanchit.fundingapplication.models.Record

class FundRepository(
        private val db: FundDatabase
) {
    suspend fun getFunds() = RetrofitInstance.api.getFunds()

    suspend fun insert(records: List<Record>) {
        records.forEach { record ->
            db.getFundsDao().insert(record)
        }

    }

     fun getSavedFunds() = db.getFundsDao().getAllFunds()
}