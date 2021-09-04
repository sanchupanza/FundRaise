package com.sanchit.fundingapplication.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object {
        const val BASE_URL = "https://testffc.nimapinfotech.com/"


        @SuppressLint("SimpleDateFormat")
        fun getDateDifference(sDate:String, eDate:String):String{
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")

            val startDate: Date = dateFormat.parse(sDate)
            val endDate: Date = dateFormat.parse(eDate)

            val diff: Long = endDate.time - startDate.time

            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            return days.toString()
        }
    }
}