package com.Jogoo.fornote.DataBase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.Jogoo.fornote.Model.Note;
import com.Jogoo.fornote.AscynNote.insertAsyncNote;
import com.Jogoo.fornote.AscynNote.deleteAsyncNote;
import com.Jogoo.fornote.AscynNote.updateAsyncNote;

import java.util.List;
// This is the Repository Class which acts as an Intermediary between our Activities and Our DAO
// it provides the Methods that use Dao  to Insert , update , Delete
public class NoteRepository {

    private NoteDataBase noteDataBase;

    public NoteRepository(Context context) {
        noteDataBase = NoteDataBase.getDatabaseInstance(context);
    }

    // This method in the Repository will let us do the Insert in the UI THREAD
    public void insertNote(Note note){
        new insertAsyncNote(noteDataBase.noteDao()).execute(note);
    }
    public void updateNote(Note note){
        new updateAsyncNote(noteDataBase.noteDao()).execute(note);
    }
    // This Method deletes with AsyncTask class
    public void deleteNote(Note note){
        new deleteAsyncNote(noteDataBase.noteDao()).execute(note);
    }
    public LiveData<List<Note>>getAllNotes(){
        return noteDataBase.noteDao().GetNotes();
    }

}
