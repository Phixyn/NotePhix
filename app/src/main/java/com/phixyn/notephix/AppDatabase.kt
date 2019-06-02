package com.phixyn.notephix

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/** Abstract class representing the app's database. */
@Database(entities = [Note::class], version = 1)
@TypeConverters(DateTypeConverter::class)
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
                        "notephix-db"
                    ).allowMainThreadQueries().build()
                    // FIXME: Improve this so we don't use DB on main thread
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}