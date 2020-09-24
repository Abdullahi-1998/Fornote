package com.Jogoo.fornote.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.Jogoo.fornote.Model.Note;

// Annotating the NoteDatabase to Database From Room listing our entities and Our Database Version
@Database(entities = {Note.class},version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    //Create A String variable and Name it Your Database Name
    public static final String DATABASE_NAME = "NOTES DATABASE";

    //Creating a Static of NoteDataBase from NoteDataBase abstract Class
    public static NoteDataBase instance;

    // Creating a Singleton For Our RoomDatabase
     public static NoteDataBase getDatabaseInstance(final Context context) {
         // If instance if Null if it's true Create a new Instance accessing the Room database Builder
         if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class,
                    DATABASE_NAME).build();
         }
         // RETURNING THE INSTANCE
        return instance;
    }
    // Creating an Abstract Class getting The DAO and naming it noteDao which will let us Access the DAO.
    public abstract NoteDao noteDao();
}
