package com.sanchit.fundingapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "funds"
)
data class Record(
    @PrimaryKey(autoGenerate = true)
    val Id: Int,
    val collectedValue: Int,
    val endDate: String,
    val mainImageURL: String,
    val shortDescription: String,
    val startDate: String,
    val title: String,
    val totalValue: Int
)