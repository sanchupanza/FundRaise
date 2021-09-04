package com.sanchit.fundingapplication.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.sanchit.fundingapplication.application.FundingApplication
import com.sanchit.fundingapplication.models.FundResponse
import com.sanchit.fundingapplication.models.Record
import com.sanchit.fundingapplication.repository.FundRepository
import com.sanchit.fundingapplication.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class FundsViewModel(
        app: Application,
        private val fundsRepository: FundRepository
) : AndroidViewModel(app) {

    private val _funds :  MutableLiveData<Resource<List<Record>>> = MutableLiveData()
    internal val funds : LiveData<Resource<List<Record>>> get()  = _funds


    init {
            getFunds()
        }

    private fun getFunds(){

        val fundsList = getSavedFunds().value

        if(fundsList!=null){
            if(fundsList.isNotEmpty()){
                _funds.postValue(Resource.Success(fundsList))
            }else{
                viewModelScope.launch {
                    getFundsFromApi()
                }
            }
        }else{
            viewModelScope.launch {
                getFundsFromApi()
            }
        }
    }

     fun getSavedFunds() = fundsRepository.getSavedFunds()


    private suspend fun getFundsFromApi() {
        _funds.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()){
                val response = fundsRepository.getFunds()
                _funds.postValue(handleResponse(response))

            }else{
                _funds.postValue(Resource.Error("No Internet connection"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> _funds.postValue(Resource.Error("Network Failure"))
                else -> _funds.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleResponse(response: Response<FundResponse>): Resource<List<Record>> {
        if (response.isSuccessful){

            response.body().let {
                viewModelScope.launch {
                    fundsRepository.insert(response.body()!!.data.Records)
                }
                return Resource.Success(response.body()!!.data.Records)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<FundingApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


}