package com.example.friends.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info (
	val seed : String,
	val results : Int,
	val page : Int,
	val version : String
) : Parcelable