package com.RickProjects.notekeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.RickProjects.notekeeper.Adapters.NotesRVAdapter;
import com.RickProjects.notekeeper.LocalDBHandlers.Operation;
import com.RickProjects.notekeeper.Models.Notes;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotesRVAdapter rvAdapter;
    private TextView rvNotesStatus;
    private Toolbar toolbar;
    private List<Notes> notesList;

    public static final String IDENTIFIER = "identifier";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesList = Operation.getInstance(getApplicationContext()).getListOfNotes();

        toolbar = findViewById(R.id.main_toolbar);
        recyclerView = findViewById(R.id.main_recyclerview);
        rvNotesStatus = findViewById(R.id.tv_main_recyclerview_status);
        rvAdapter = new NotesRVAdapter(this, notesList);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (!notesList.isEmpty()) {
            rvNotesStatus.setVisibility(View.GONE);
        }

        rvAdapter.setOnClick(new NotesRVAdapter.OnClick() {
            @Override
            public void onItemClick(int position, UUID uuid) {
                Intent intent = new Intent(MainActivity.this, ViewNotes.class);
                intent.putExtra(IDENTIFIER, uuid);
                startActivity(intent);
                finish();
            }
        });
    }

    //The method is called by the Floating Action Button.
    public void createNote(View view) {
        startActivity(new Intent(this, CreateNotes.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
