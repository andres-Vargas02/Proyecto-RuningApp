package com.uptc.runningapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserSession")
data class UserSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int
)