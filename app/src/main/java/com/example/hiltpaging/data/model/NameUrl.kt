package com.example.hiltpaging.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NameUrl(
    val name: String,
    val url: String
): Parcelable