package com.sanchit.fundingapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sanchit.fundingapplication.R
import com.sanchit.fundingapplication.databinding.SingleImageItemLayoutBinding
import com.sanchit.fundingapplication.databinding.SingleItemLayoutBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val images:List<String>):SliderViewAdapter<SliderAdapter.MyViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?): MyViewHolder {
        this.context  = parent!!.context
        val binding: SingleImageItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.single_image_item_layout,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = images[position]
        Glide.with(context)
            .load(current)
            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.imageview)
    }
    override fun getCount()=images.size



    class MyViewHolder(val binding: SingleImageItemLayoutBinding): SliderViewAdapter.ViewHolder(binding.root)




}