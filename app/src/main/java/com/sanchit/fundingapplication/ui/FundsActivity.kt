package com.sanchit.fundingapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sanchit.fundingapplication.R
import com.sanchit.fundingapplication.databinding.ActivityFundsBinding
import com.sanchit.fundingapplication.db.FundDatabase
import com.sanchit.fundingapplication.repository.FundRepository
import com.sanchit.fundingapplication.util.Resource

class FundsActivity : AppCompatActivity() {


    lateinit var viewModel: FundsViewModel

    private var _binding:ActivityFundsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFundsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fundsRepository = FundRepository(FundDatabase(this))
        val viewModelFactory = FundsViewModelProviderFactory(application,fundsRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(FundsViewModel::class.java)

        viewModel.funds.observe(this, Observer {response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {fundResponse ->
                        if(fundResponse.data.Records.isNotEmpty()){
                            Toast.makeText(this, ""+fundResponse.data.Records.size, Toast.LENGTH_LONG).show()

                        }
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(this, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
    }


    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE

    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}