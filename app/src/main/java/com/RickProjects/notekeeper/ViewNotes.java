package com.RickProjects.notekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.RickProjects.notekeeper.LocalDBHandlers.Operation;
import com.RickProjects.notekeeper.Models.Notes;

import java.util.UUID;

public class ViewNotes extends AppCompatActivity {
    private TextView noteTitle, noteContent, noteDateCreated, noteCategory;
    private Toolbar toolbar;
    public static final String IDENTIFIER = "identifier";

    UUID uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        uuid = (UUID) getIntent().getSerializableExtra(MainActivity.IDENTIFIER);
        toolbar = findViewById(R.id.view_notes_toolbar);
        noteTitle = findViewById(R.id.tv_note_title);
        noteContent = findViewById(R.id.tv_note_content);
        noteContent = findViewById(R.id.tv_note_content);
        noteDateCreated = findViewById(R.id.tv_note_date_created);
        noteCategory = findViewById(R.id.tv_note_category);
        Notes notes = Operation.getInstance(getApplicationContext()).getNote(uuid);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        noteTitle.setText(notes.getNoteTitle());
        noteContent.setText(notes.getNoteContents());
        noteDateCreated.setText(notes.getDateCreated());
        noteCategory.setText(notes.getNoteCategory());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_note:
                Intent intent = new Intent(this, EditNotes.class);
                intent.putExtra(IDENTIFIER, uuid);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_delete_note:
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
                deleteDialog.setMessage(R.string.dialog_delete_message)
                        .setPositiveButton(R.string.dialog_positive_feedback, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Operation.getInstance(getApplicationContext()).deleteNote(uuid);
                                startActivity(new Intent(ViewNotes.this, MainActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.dialog_negative_feedback, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create()
                        .show();
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
}