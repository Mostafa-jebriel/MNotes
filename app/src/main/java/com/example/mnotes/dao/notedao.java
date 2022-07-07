package com.example.mnotes.dao;


import android.app.SearchManager;
import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.example.mnotes.classes.note;

import java.util.List;

@Dao
public interface notedao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<note> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertnotes(note note);
    @Delete
    void deletenotes(note note);

}
