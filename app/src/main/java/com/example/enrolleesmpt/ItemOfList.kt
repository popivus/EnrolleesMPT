package com.example.enrolleesmpt

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemOfList(
    val name : String,
    val qualification : String,
    val trainingPeriod : String,
    val description : String,
    val urlToImage : String
) : Parcelable