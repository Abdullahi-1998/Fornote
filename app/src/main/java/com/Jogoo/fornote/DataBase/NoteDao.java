package com.Jogoo.fornote.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.Jogoo.fornote.Model.Note;

import java.util.List;

// Annotating  Dao Object and Creating the Interface
// DATA ACCESS OBJECT
@Dao
public interface NoteDao {
    // Inserting into Our Database using Insert annotation
    @Insert
    long[] InsertNote(Note... note);

    //Updating our Database using the @Update annotation
    @Update
    void UpdateNote(Note... note);

    // This is also the Delete Annotation for Deleting
    @Delete
    int DeleteNote(Note... note);

    // Querying  our Database list using LiveData
    // "Select all From Notes table" referring to Our Table created in our Note Class.
    @Query("SELECT * FROM Notes")
    LiveData<List<Note>> GetNotes();
}
