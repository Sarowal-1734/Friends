package com.example.friends.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinates (

	val latitude : String,
	val longitude : String
) : Parcelable