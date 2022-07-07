package com.example.mnotes.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mnotes.classes.note;
import com.example.mnotes.dao.notedao;

@Database(entities = note.class ,version = 1,exportSchema = false)
public abstract class notedatabase extends RoomDatabase {

    private static notedatabase notedatabase;

    public static synchronized notedatabase getdatabase(Context context){
        if(notedatabase==null){
            notedatabase= Room.databaseBuilder(
                    context,
                    notedatabase.class,
                    "notes_db"
            ).build();
        }
        return notedatabase;
    }

    public abstract notedao notedao();
}
