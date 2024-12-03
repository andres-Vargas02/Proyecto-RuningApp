package com.uptc.runningapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RaceLocal")
data class RaceLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "race_id") val raceId: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "race_name") val raceName: String,
    val distance: Float,
    val duration: Long,
    val date: String
)
