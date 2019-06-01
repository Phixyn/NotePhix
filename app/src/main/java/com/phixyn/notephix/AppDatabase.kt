package com.phixyn.notephix

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/** Abstract class representing the app's database. */
@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // Declare an abstract function for each DAO, which returns the DAO.
    abstract fun noteDao(): NoteDao

    /**
     * Provides static methods to create and destroy a singleton instance of the Room database.
     */
    companion object {
        var INSTANCE: AppDatabase? = null

        /**
         * Builds a new instance of the Room database and returns it.
         */
        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    // Build Room database
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "appDB"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}