package com.sanchit.fundingapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sanchit.fundingapplication.R
import com.sanchit.fundingapplication.databinding.SingleItemLayoutBinding
import com.sanchit.fundingapplication.models.Record
import com.sanchit.fundingapplication.util.Constants
import com.sanchit.fundingapplication.util.Constants.Companion.percent
import com.squareup.picasso.Picasso

class FundsAdapter(private val records: List<Record>) :RecyclerView.Adapter<FundsAdapter.MyViewHolder>(){
    lateinit var context:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        this.context  =parent.context
        val binding: SingleItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.single_item_layout,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val current  = records[position]

        holder.binding.tvTitle.text = current.title
        holder.binding.tvDescription.text = current.shortDescription
        holder.binding.tvFunded.text = current.collectedValue.toString()
        holder.binding.tvGoals.text = current.totalValue.toString()
        holder.binding.tvEndsIn.text = Constants.getDateDifference(current.startDate,current.endDate)
        holder.binding.tvPercentage.text = current.collectedValue.percent(current.totalValue).toString()+"%"

        Glide.with(context)
            .load(current.mainImageURL)
            .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.imageview)
    //    Picasso.get().load(current.mainImageURL).placeholder(R.drawable.ic_launcher_foreground).into(holder.binding.imageview)


    }

    override fun getItemCount()  = records.size

    class MyViewHolder(val binding: SingleItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

}