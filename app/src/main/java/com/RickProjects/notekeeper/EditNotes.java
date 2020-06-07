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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.RickProjects.notekeeper.LocalDBHandlers.Operation;
import com.RickProjects.notekeeper.Models.Notes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EditNotes extends AppCompatActivity {
    UUID uuid;
    private EditText noteTitle, noteContents;
    private Toolbar toolbar;
    private Spinner noteCategories;
    public static final String IDENTIFIER = "identifier";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);


        uuid = (UUID) getIntent().getSerializableExtra(ViewNotes.IDENTIFIER);
        toolbar = findViewById(R.id.editNotes_toolbar);
        noteTitle = findViewById(R.id.et_note_title);
        noteContents = findViewById(R.id.et_note_contents);
//        noteCategories = findViewById(R.id.spinner_categories);
        final Notes notes = Operation.getInstance(getApplicationContext()).getNote(uuid);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        noteTitle.setText(notes.getNoteTitle());
        noteContents.setText(notes.getNoteContents());

//        List<String> categoriesList = new ArrayList<>();
//        categoriesList.add("Home");
//        categoriesList.add("Personal");
//        categoriesList.add("Chores");
//        categoriesList.add("Quotes");
//        categoriesList.add("Assignments");

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoriesList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        this.noteCategories.setAdapter(adapter);
//        noteCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                if (!notes.getNoteCategory().equals(null)) {
////                    setCategorySelected(notes.getNoteCategory());
////                }
//                String itemSelected = parent.getItemAtPosition(position).toString();
//                setCategorySelected(itemSelected);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                setCategorySelected(notes.getNoteCategory());
//            }
//        });
    }

//    private String categorySelected;
//
//    private String getCategorySelected() {
//        return categorySelected;
//    }
//
//    private void setCategorySelected(String categorySelected) {
//        this.categorySelected = categorySelected;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_update:
                //Update Note Details.
                Operation.getInstance(getApplicationContext()).updateNote(
                        uuid,
                        noteTitle.getText().toString(), //Updated Title
                        noteContents.getText().toString(), //Updated Contents
                        DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime()) //Date Edited
//                        getCategorySelected() //Updated Category
                );
                Intent intent = new Intent(this, ViewNotes.class);
                intent.putExtra(IDENTIFIER, uuid);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_delete_note:
                //Delete Note
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
                deleteDialog.setMessage(R.string.dialog_delete_message)
                        .setPositiveButton(R.string.dialog_positive_feedback, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Delete Note
                                Operation.getInstance(getApplicationContext()).deleteNote(uuid);
                                startActivity(new Intent(EditNotes.this,
                                        ViewNotes.class).putExtra(IDENTIFIER, uuid));
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.dialog_negative_feedback, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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
        finish();
    }
}