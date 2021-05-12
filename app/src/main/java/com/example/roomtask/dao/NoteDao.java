package com.example.roomtask.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomtask.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    public void insert(Note note);

    @Update
    public void update(Note note);

    @Delete
    public void delete(Note note);

    @Query("DELETE FROM tbl_note")
    public void deleteAll();

    @Query("SELECT * FROM tbl_note ORDER BY priority DESC")
    public LiveData<List<Note>> findAllNotes();
}
