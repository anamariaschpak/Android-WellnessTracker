package com.example.wellnesstracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note newNote);

    @Query("Select * from note")
    List<Note> getAllNotes();

    @Query("Update note set content=:newContent where userId=:currentUserId")
    void editNote(String newContent, int currentUserId);

    @Query("Select * from note where userId=:userId and date=:selectedDate")
    Note getNoteByUserIdAndDate(int userId, String selectedDate);

    @Query("Delete from note where userId=:currentUserId")
    void deleteNoteByUserId(int currentUserId);

    @Query("Delete from note")
    void deleteAllNotes();
}
