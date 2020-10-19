package com.RickProjects.myNotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.RickProjects.myNotes.EXTRA_TITLE";
    public static final String EXTRA_ID = "com.RickProjects.myNotes.EXTRA_ID";
    public static final String EXTRA_DESCRIPTION = "com.RickProjects.myNotes.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE_CREATED = "com.RickProjects.myNotes.EXTRA_DATE_CREATED";

    private TextInputEditText editTextTitle, editTextDescription;
//    private CoordinatorLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        editTextTitle = findViewById(R.id.editText_title);
        editTextDescription = findViewById(R.id.editText_description);
//        mLayout = findViewById(R.id.layout_createNotes);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle(getString(R.string.edit_note));
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle(getString(R.string.create_note));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_edit_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNote();
                return true;
            case R.id.action_share:
                shareNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (!title.trim().isEmpty()) {
            if (!description.trim().isEmpty()) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n\n" + description);
                shareIntent.setType("text/plain"); //Will Only Open email clients.
                startActivity(Intent.createChooser(shareIntent, "Share"));
            } else {
                Toast.makeText(this, "Note Description is required", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Note Title is required!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String date_created = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "CANNOT SAVE EMPTY NOTE!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_DATE_CREATED, date_created);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, data);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void erase() {
        editTextTitle.setText(null);
        editTextDescription.setText(null);
        editTextTitle.requestFocus();
    }
}