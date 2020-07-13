package com.RickProjects.myNotes;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    public static final int CREATE_NOTE_REQUEST = 1;
    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_note_list);
        NoteListAdapter adapter = new NoteListAdapter();
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNoteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        findViewById(R.id.fab_addNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(NoteListActivity.this, CreateNoteActivity.class),
                        CREATE_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_NOTE_REQUEST && resultCode == RESULT_OK) {
            mNoteViewModel.insert(new Note(
                    data.getStringExtra(CreateNoteActivity.EXTRA_TITLE),
                    data.getStringExtra(CreateNoteActivity.EXTRA_DESCRIPTION),
                    data.getStringExtra(CreateNoteActivity.EXTRA_DATE_CREATED)
            ));
        }
    }
}
