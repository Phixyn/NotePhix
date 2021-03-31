package com.phixyn.notephix

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/** Data Access Object for notes. */
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>
//    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id IN (:noteIds)")
    fun getAllByIds(noteIds: IntArray): List<Note>

    @Query("SELECT * FROM note WHERE id=:noteId")
    fun findById(noteId: Int): Note

    @Insert
    fun insertNote(note: Note)

    @Insert
    fun insertAll(vararg notes: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}