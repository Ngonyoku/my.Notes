package com.RickProjects.myNotes;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    public static final int CREATE_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_note_list);
        final NoteListAdapter adapter = new NoteListAdapter();
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNoteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(NoteListActivity.this)
                        .setMessage(R.string.delete_msg)
                        .setTitle(R.string.delete)
                        .setPositiveButton(R.string.am_sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mNoteViewModel.delete(adapter.getNoteAt(viewHolder.getLayoutPosition()));
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(NoteListActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClick(
                note -> {
                    Intent intent = new Intent(NoteListActivity.this, CreateEditNoteActivity.class);
                    intent.putExtra(CreateEditNoteActivity.EXTRA_ID, note.getId());
                    intent.putExtra(CreateEditNoteActivity.EXTRA_TITLE, note.getTitle());
                    intent.putExtra(CreateEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                    intent.putExtra(CreateEditNoteActivity.EXTRA_DATE_CREATED, note.getDateCreated());

                    startActivityForResult(intent, EDIT_NOTE_REQUEST);
                }
        );

        findViewById(R.id.fab_addNote).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(
                                new Intent(NoteListActivity.this, CreateEditNoteActivity.class),
                                CREATE_NOTE_REQUEST);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_NOTE_REQUEST && resultCode == RESULT_OK) {
            mNoteViewModel.insert(new Note(
                    data.getStringExtra(CreateEditNoteActivity.EXTRA_TITLE),
                    data.getStringExtra(CreateEditNoteActivity.EXTRA_DESCRIPTION),
                    data.getStringExtra(CreateEditNoteActivity.EXTRA_DATE_CREATED)
            ));
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(CreateEditNoteActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note Canceled", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(CreateEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(CreateEditNoteActivity.EXTRA_DESCRIPTION);
            String date = data.getStringExtra(CreateEditNoteActivity.EXTRA_DATE_CREATED);

            Note note = new Note(title, description, date);
            note.setId(id);
            mNoteViewModel.update(note);
            messageFeedback("Note Updated");
        } else {
            Toast.makeText(this, "Not not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_deleteAll:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.delete_all)
                        .setMessage("Are you sure you want to Delete All of your Notes")
                        .setPositiveButton(R.string.am_sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mNoteViewModel.deleteAll();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

                return true;
            case R.id.menu_exitApp:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void messageFeedback(String msg) {
        Snackbar.make(findViewById(R.id.layout_note_list), msg, Snackbar.LENGTH_SHORT).show();
    }

    private void messageFeedback(String msg, int len) {
        if (len == 1) messageFeedback(msg);
        else Snackbar.make(findViewById(R.id.layout_note_list), msg, Snackbar.LENGTH_LONG).show();

    }
}
