package com.RickProjects.myNotes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.DateFormat;
import java.util.Calendar;

@Database(entities = {Note.class, Category.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Note_database";
    private static NoteDatabase instance;

    public abstract NoteDAO noteDAO();

    public abstract CategoryDAO categoryDAO();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            NoteDatabase.class,
                            DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            ;
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBTask(instance).execute();
        }
    };

    private static class PopulateDBTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO mNoteDAO;
        private CategoryDAO mCategoryDAO;

        public PopulateDBTask(NoteDatabase database) {
            mNoteDAO = database.noteDAO();
            mCategoryDAO = database.categoryDAO();
        }

        @Override
        protected Void doInBackground(Note... notes) {
            Category categoryOne = new Category("One");
            Category categoryTwo = new Category("Two");

            Note noteOne = new Note(
                    "Note Title",
                    "Description of the Note",
                    DateFormat.getDateInstance(DateFormat.FULL)
                            .format(Calendar.getInstance().getTime())
            );

            noteOne.setCategoryId(categoryOne.getCategoryId());

            mCategoryDAO.insert(categoryOne);
            mCategoryDAO.insert(categoryTwo);
            mNoteDAO.insert(noteOne);
            return null;
        }
    }
}
