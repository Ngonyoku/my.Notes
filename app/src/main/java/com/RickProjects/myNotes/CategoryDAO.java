package com.RickProjects.myNotes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert
    void insert(Category category);

    @Delete
    void delete(Category category);

    @Transaction
    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getCategories();
}
