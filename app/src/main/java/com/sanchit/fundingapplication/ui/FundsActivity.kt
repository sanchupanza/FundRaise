package com.sanchit.fundingapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanchit.fundingapplication.adapter.FundsAdapter
import com.sanchit.fundingapplication.databinding.ActivityFundsBinding
import com.sanchit.fundingapplication.db.FundDatabase
import com.sanchit.fundingapplication.models.Record
import com.sanchit.fundingapplication.repository.FundRepository
import com.sanchit.fundingapplication.util.Resource

class FundsActivity : AppCompatActivity() {


    lateinit var viewModel: FundsViewModel

    private var _binding: ActivityFundsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFundsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Record List"

        val fundsRepository = FundRepository(FundDatabase(this))
        val viewModelFactory = FundsViewModelProviderFactory(application, fundsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FundsViewModel::class.java)


        binding.rvFunding.layoutManager = LinearLayoutManager(this)
        binding.rvFunding.setHasFixedSize(true)




        viewModel.funds.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(this, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.getSavedFunds().observe(this, Observer { records ->
            records?.let {
                if (records.isNotEmpty()) {
                    setDataToRv(records)
                } else {
                    viewModel.getFunds()
                }
            }
        })
    }

    private fun setDataToRv(records: List<Record>) {
        binding.rvFunding.setItemViewCacheSize(records.size)
        binding.rvFunding.adapter = FundsAdapter(records)

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