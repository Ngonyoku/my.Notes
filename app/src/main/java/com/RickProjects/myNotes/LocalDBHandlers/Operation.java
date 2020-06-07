package com.RickProjects.myNotes.LocalDBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.RickProjects.myNotes.Models.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Operation {
    private static Operation instance;
    private SQLiteDatabase myDatabase;

    private Operation(Context context) {
        myDatabase = new MyNotesDBHelper(context).getWritableDatabase();

    }

    public List<Notes> getListOfNotes() {
        Cursor cursor = myDatabase.query(
                MyNotesDBHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<Notes> notesList = new ArrayList<>();

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                String ID = cursor.getString(cursor.getColumnIndex(MyNotesDBHelper.COLUMN_UUID));
                String title = cursor.getString(cursor.getColumnIndex(MyNotesDBHelper.COLUMN_TITLE));
                String contents = cursor.getString(cursor.getColumnIndex(MyNotesDBHelper.COLUMN_CONTENTS));
                String dateCreated = cursor.getString(cursor.getColumnIndex(MyNotesDBHelper.COLUMN_DATE_CREATED));
                String category = cursor.getString(cursor.getColumnIndex(MyNotesDBHelper.COLUMN_CATEGORY));

                Notes notes = new Notes(UUID.fromString(ID));
                notes.setNoteTitle(title);
                notes.setNoteCategory(category);
                notes.setNoteContents(contents);
                notes.setDateCreated(dateCreated);

                notesList.add(notes);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return notesList;
    }

    public static Operation getInstance(Context context) {
        if (instance == null) {
            instance = new Operation(context);
        }

        return instance;
    }

    public void updateNote(UUID ID, String title, String contents, String dateEdited/*,String category*/) {
        Notes notes = new Notes(ID);
        notes.setNoteTitle(title);
        notes.setNoteContents(contents);
        notes.setDateCreated(dateEdited);
        notes.setNoteCategory(notes.getNoteCategory());
        myDatabase.update(
                MyNotesDBHelper.TABLE_NAME,
                getContentValues(notes),
                MyNotesDBHelper.COLUMN_UUID + " = ?",
                new String[]{ID.toString()}
        );
    }

    public void deleteNote(UUID ID) {
        myDatabase.delete(
                MyNotesDBHelper.TABLE_NAME,
                MyNotesDBHelper.COLUMN_UUID + " = ?",
                new String[]{ID.toString()}
        );
    }

    public Notes getNote(UUID ID) {
        Cursor cursor = myDatabase.query(
                MyNotesDBHelper.TABLE_NAME,
                null,
                MyNotesDBHelper.COLUMN_UUID + " = ?",
                new String[]{ID.toString()},
                null,
                null,
                null
        );
        try {
            cursor.moveToFirst();
            Notes notes = new Notes(ID);
            notes.setNoteTitle(cursor.getString(cursor.getColumnIndex(MyNotesDBHelper.COLUMN_TITLE)));
            notes.setNoteContents(cursor.getString(cursor.getColumnIndex(MyNotesDBHelper.COLUMN_CONTENTS)));
            notes.setDateCreated(cursor.getString(cursor.getColumnIndex(MyNotesDBHelper.COLUMN_DATE_CREATED)));
            notes.setNoteCategory(cursor.getString(cursor.getColumnIndex(MyNotesDBHelper.COLUMN_CATEGORY)));

            return notes;
        } finally {
            cursor.close();
        }
    }

    public void addNote(Notes notes) {
        myDatabase.insert(
                MyNotesDBHelper.TABLE_NAME,
                null,
                getContentValues(notes)
        );
    }

    private ContentValues getContentValues(Notes notes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyNotesDBHelper.COLUMN_UUID, notes.getIdentifier().toString());
        contentValues.put(MyNotesDBHelper.COLUMN_TITLE, notes.getNoteTitle());
        contentValues.put(MyNotesDBHelper.COLUMN_CONTENTS, notes.getNoteContents());
        contentValues.put(MyNotesDBHelper.COLUMN_CATEGORY, notes.getNoteCategory());
        contentValues.put(MyNotesDBHelper.COLUMN_DATE_CREATED, notes.getDateCreated());

        return contentValues;
    }
}
