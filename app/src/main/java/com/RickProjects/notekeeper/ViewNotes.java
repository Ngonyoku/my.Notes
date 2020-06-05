package com.RickProjects.notekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.RickProjects.notekeeper.LocalDBHandlers.Operation;
import com.RickProjects.notekeeper.Models.Notes;

import java.util.UUID;

public class ViewNotes extends AppCompatActivity {
    private UUID uuid;
    private TextView noteTitle, noteContent, noteDateCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        uuid = (UUID) getIntent().getSerializableExtra(MainActivity.IDENTIFIER);
        noteTitle = findViewById(R.id.tv_note_title);
        noteContent = findViewById(R.id.tv_note_content);
        noteContent = findViewById(R.id.tv_note_content);
        noteDateCreated = findViewById(R.id.tv_note_date_created);
        Notes notes = Operation.getInstance(getApplicationContext()).getNote(uuid);

        noteTitle.setText(notes.getNoteTitle());
        noteContent.setText(notes.getNoteContents());
        noteDateCreated.setText(notes.getDateCreated());

    }
}