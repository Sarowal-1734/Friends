package com.example.friends.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val city: String,
    val country: String,
    val state: String
) : Parcelable