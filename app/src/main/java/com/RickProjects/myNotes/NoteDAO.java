package com.RickProjects.myNotes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM Note_table")
    void deleteAll();

    @Query("SELECT * FROM Note_table ORDER BY note_id DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM Note_table WHERE note_category_id = :categoryId")
    LiveData<List<Note>> getNotesForCategory(int categoryId);
}
