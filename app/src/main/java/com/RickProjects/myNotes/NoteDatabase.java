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

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDAO noteDAO();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            NoteDatabase.class,
                            "Note_database")
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
        private NoteDAO mDAO;

        public PopulateDBTask(NoteDatabase database) {
            mDAO = database.noteDAO();
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mDAO.insert(new Note(
                            "Note Title",
                            "Description of the Note",
                            DateFormat.getDateInstance(DateFormat.FULL)
                                    .format(Calendar.getInstance().getTime())
                    )
            );
            return null;
        }
    }
}
