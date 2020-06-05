package com.RickProjects.notekeeper.LocalDBHandlers;

import android.content.Context;

import com.RickProjects.notekeeper.Models.Notes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Operation {
    private static Operation instance;
    private List<Notes> notesList;

    private Operation(Context context) {
        notesList = new ArrayList<>();
    }

    public List<Notes> getListOfNotes() {
        return notesList;
    }

    public static Operation getInstance(Context context) {
        if (instance == null) {
            instance = new Operation(context);
        }

        return instance;
    }

    public Notes getNote(UUID ID) {
        for (Notes notes : notesList) {
            if (notes.getIdentifier().equals(ID)) {
                return notes;
            }
        }

        return null;
    }

    public void addNote(Notes notes) {
        notesList.add(notes);
    }
}