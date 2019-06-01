package com.phixyn.notephix

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converter used to serialize java.util.Date objects in the app's Room database.
 */
class DateTypeConverter {
    /**
     * Converts the Long [value] from the Room database into a Date object.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)

    /**
     * Converts [date] into a Long which can be stored in the Room database.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}