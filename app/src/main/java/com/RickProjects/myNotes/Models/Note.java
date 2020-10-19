package com.RickProjects.myNotes.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "note_title")
    private String title;

    @NonNull
    @ColumnInfo(name = "note_description")
    private String description;

    @ColumnInfo(name = "note_date")
    private String dateCreated;

    public Note(@NonNull String title, @NonNull String description, String dateCreated) {
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
