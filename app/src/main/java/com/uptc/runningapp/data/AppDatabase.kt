package com.uptc.runningapp.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.uptc.runningapp.data.DAO.UserSessionDao

@Database(entities = [UserSession::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userSessionDao(): UserSessionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            context.deleteDatabase("user_session_database")

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "new_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
