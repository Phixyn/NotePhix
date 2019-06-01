package com.phixyn.notephix

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/** Data class for notes. */
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String = "",
    val body: String,
    @ColumnInfo(name = "created_on")
    val createdOn: Date,
    @ColumnInfo(name = "updated_on")
    val updatedOn: Date
)
