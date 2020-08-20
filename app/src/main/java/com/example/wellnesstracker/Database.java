package com.example.wellnesstracker;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {User.class, Note.class, Exercise.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static final String DB_NAME = "projectDatabase.db";
    private static Database instance;

    public static Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, Database.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract NoteDao getNoteDao();
    public abstract UserDao getUserDao();
    public abstract ExerciseDao getExerciseDao();

}
