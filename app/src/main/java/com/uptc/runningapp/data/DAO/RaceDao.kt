package com.uptc.runningapp.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.uptc.runningapp.data.RaceLocal

@Dao
interface RaceDao {

    @Insert
    suspend fun insertRace(race: RaceLocal)

    @Insert
    suspend fun insertRaces(races: List<RaceLocal>)

    @Query("SELECT * FROM RaceLocal")
    suspend fun getAllRaces(): List<RaceLocal>

    @Query("SELECT * FROM RaceLocal WHERE race_id = :raceId")
    suspend fun getRaceById(raceId: Int): RaceLocal?

    @Query("SELECT * FROM RaceLocal WHERE user_id = :userId")
    suspend fun getRacesByUserId(userId: Int): List<RaceLocal>

    @Update
    suspend fun updateRace(race: RaceLocal)

    @Query("DELETE FROM RaceLocal WHERE race_id = :raceId")
    suspend fun deleteRaceById(raceId: Int)
}
