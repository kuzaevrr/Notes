package com.kuzaev.notes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.kuzaev.notes.object.Note;

import java.util.List;

@Dao
public interface NotesDAO {

    @Query("SELECT * FROM notes ORDER BY dayOfWeek ASC")//DESC обратный порядок ASC - прямой порядок
    List<Note> getAllNote();

    @Insert
    void insertNote(Note note);

    @Delete
    void deletedNote(Note note);

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
