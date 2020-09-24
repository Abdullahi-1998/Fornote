package com.Jogoo.fornote.AscynNote;

import android.os.AsyncTask;

import com.Jogoo.fornote.DataBase.NoteDao;
import com.Jogoo.fornote.Model.Note;

public class updateAsyncNote extends AsyncTask<Note,Void,Void> {
    private NoteDao noteDao;


    public updateAsyncNote(NoteDao dao) {
        noteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.UpdateNote(notes);
        return null;
    }
}
