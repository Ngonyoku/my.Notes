package com.RickProjects.notekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.RickProjects.notekeeper.LocalDBHandlers.Operation;
import com.RickProjects.notekeeper.Models.Notes;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Calendar;


public class CreateNotes extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText noteTitle, noteContents;
    private CoordinatorLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        noteTitle = findViewById(R.id.et_note_title);
        noteContents = findViewById(R.id.et_note_contents);
        toolbar = findViewById(R.id.createNotes_toolbar);
        layout = findViewById(R.id.createNotes_layout);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                Notes notes = new Notes();
                if (noteTitle.getText().toString().trim().equals("") && noteContents.getText().toString().trim().equals("")) {
                    Snackbar.make(layout, "Nothing to Add", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (noteTitle.getText().toString().trim().equals("") && !noteContents.getText().toString().trim().equals("")) {
                        noteTitle.setText("--No Title--");
                    }
                    notes.setNoteTitle(noteTitle.getText().toString());
                    notes.setNoteContents(noteContents.getText().toString());
                    notes.setDateCreated(DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime()));
                    Operation.getInstance(getApplicationContext()).addNote(notes);
                    Snackbar.make(layout, "Note Added", Snackbar.LENGTH_SHORT)
                            .setAction("VIEW", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(CreateNotes.this, MainActivity.class));
                                    finish();
                                }
                            })
                            .show();
                }
                erase();
                return true;
            case R.id.menu_categories:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void erase() {
        noteTitle.setText(null);
        noteContents.setText(null);
    }
}
