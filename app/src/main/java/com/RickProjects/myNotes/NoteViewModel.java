package com.RickProjects.myNotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private final NoteRepository mRepository;
    private LiveData<List<Note>> mNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mNotes = mRepository.getNotes();
    }

    public void insert(Note note) {
        mRepository.insert(note);
    }

    public void delete(Note note) {
        mRepository.delete(note);
    }

    public void update(Note note) {
        mRepository.update(note);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public LiveData<List<Note>> getNotes() {
        return mNotes;
    }
}
