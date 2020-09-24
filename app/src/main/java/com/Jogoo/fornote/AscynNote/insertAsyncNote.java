package com.Jogoo.fornote.AscynNote;

import android.os.AsyncTask;

import com.Jogoo.fornote.DataBase.NoteDao;
import com.Jogoo.fornote.Model.Note;

public class insertAsyncNote extends AsyncTask<Note,Void,Void> {
    // Creating a NEW instance of NoteDao Interface
    private NoteDao noteDao;

    // THEN Creating a Constructor taking an Input as NoteDao Interface
    public insertAsyncNote(NoteDao Dao) {
        noteDao = Dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        // Calling the InsertNote From the DATA ACCESS OBJECT an Inserting
        // This method will get Called in the Background not in the Main Thread
        noteDao.InsertNote(notes);
        return null;
    }
}
