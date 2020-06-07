package com.RickProjects.myNotes.LocalDBHandlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyNotesDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myNotes.db"; //Database Name
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notes_table"; //Table Name.

    //Columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_UUID = "uuid";
    public static final String COLUMN_TITLE = "note_title";
    public static final String COLUMN_CONTENTS = "note_contents";
    public static final String COLUMN_DATE_CREATED = "date_created";
    public static final String COLUMN_CATEGORY = "note_category";

    public MyNotesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_UUID + ","
                + COLUMN_TITLE + ","
                + COLUMN_CONTENTS + ","
                + COLUMN_DATE_CREATED + " ,"
                + COLUMN_CATEGORY
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
