package com.sanchit.fundingapplication.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sanchit.fundingapplication.repository.FundRepository

class FundsViewModelProviderFactory(
        private val app: Application,
        private val fundsRepository: FundRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FundsViewModel(app,fundsRepository) as T
    }
}