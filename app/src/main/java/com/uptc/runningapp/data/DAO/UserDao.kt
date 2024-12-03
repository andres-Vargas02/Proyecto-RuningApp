package com.uptc.runningapp.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.uptc.runningapp.data.UserSession

@Dao
interface UserSessionDao {
    @Insert
    suspend fun insertUserSession(userSession: UserSession)

    @Query("SELECT userId FROM UserSession ORDER BY id DESC LIMIT 1")
    suspend fun getUserId(): Int?

    @Query("DELETE FROM UserSession")
    suspend fun deleteAllSessions()
}



