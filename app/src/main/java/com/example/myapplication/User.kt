package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val password: String,
    val birthDate: Long,
    val acceptedConditions: Boolean,
    val picture: Picture
)

data class Picture(
    val large: String,
)

data class PictureResponse(
    val results: List<Picture>
)