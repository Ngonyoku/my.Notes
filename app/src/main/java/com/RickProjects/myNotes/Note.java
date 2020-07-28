package com.RickProjects.myNotes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "Note_table",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "category_id",
                childColumns = "note_category_id"
        )
)
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int noteId;

    @ColumnInfo(name = "note_category_id")
    private int categoryId;

    @ColumnInfo(name = "note_title")
    private String title;

    @ColumnInfo(name = "note_description")
    private String description;

    @ColumnInfo(name = "note_date")
    private String dateCreated;

    public Note(@NonNull String title, @NonNull String description, String dateCreated) {
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
}
