package com.example.friends.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Registered (

	val date : String,
	val age : Int
) : Parcelable