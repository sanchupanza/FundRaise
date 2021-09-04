package com.sanchit.fundingapplication.repository

import com.sanchit.fundingapplication.api.RetrofitInstance
import com.sanchit.fundingapplication.db.FundDatabase

class FundRepository(
    val db: FundDatabase
) {
    suspend fun getFunds() = RetrofitInstance.api.getFunds()

    fun getSavedFunds() = db.getFundsDao().getAllFunds()
}