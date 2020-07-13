package com.RickProjects.myNotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.RickProjects.myNotes.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.RickProjects.myNotes.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE_CREATED = "com.RickProjects.myNotes.EXTRA_DATE_CREATED";

    private TextInputEditText editTextTitle, editTextDescription;
    private CoordinatorLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        editTextTitle = findViewById(R.id.editText_title);
        editTextDescription = findViewById(R.id.editText_description);
        mLayout = findViewById(R.id.layout_createNotes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String date_created = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Empty Note!", Toast.LENGTH_SHORT).show();
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_DATE_CREATED, date_created);
        setResult(RESULT_OK, data);

        Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        erase();
    }

    private void erase() {
        editTextTitle.setText(null);
        editTextDescription.setText(null);
        editTextTitle.requestFocus();
    }
}