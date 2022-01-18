package com.example.friends.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val id: Id,
    val cell: String?,
    val email: String?,
    val location: Location,
    val name: Name,
    val phone: String?,
    val picture: Picture
) : Parcelable