package com.RickProjects.myNotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.RickProjects.myNotes.LocalDBHandlers.Operation;
import com.RickProjects.myNotes.Models.Notes;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CreateNotes extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText noteTitle, noteContents;
    private CoordinatorLayout layout;
    private Spinner categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        noteTitle = findViewById(R.id.et_note_title);
        noteContents = findViewById(R.id.et_note_contents);
        toolbar = findViewById(R.id.createNotes_toolbar);
        layout = findViewById(R.id.createNotes_layout);
        categories = findViewById(R.id.spinner_categories);

        toolbar.setTitle("Create Note");
        setSupportActionBar(toolbar);

        List<String> categoriesList = new ArrayList<>();
        categoriesList.add("Home");
        categoriesList.add("Personal");
        categoriesList.add("Chores");
        categoriesList.add("Quotes");
        categoriesList.add("Assignments");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoriesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.categories.setAdapter(adapter);
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = parent.getItemAtPosition(position).toString();
                setCategorySelected(itemSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String categorySelected;

    private String getCategorySelected() {
        return categorySelected;
    }

    private void setCategorySelected(String categorySelected) {
        this.categorySelected = categorySelected;
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
                    notes.setNoteCategory(getCategorySelected());
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
