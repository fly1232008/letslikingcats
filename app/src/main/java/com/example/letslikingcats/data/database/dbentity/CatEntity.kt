package com.example.letslikingcats.data.database.dbentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatEntity(
    @PrimaryKey val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val favorite: Boolean
)