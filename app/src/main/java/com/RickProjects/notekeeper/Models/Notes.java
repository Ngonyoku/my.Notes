package com.RickProjects.notekeeper.Models;

import java.util.UUID;

public class Notes {
    private UUID identifier;
    private String noteTitle, dateCreated, noteContents;

    public Notes() {
        this.identifier = UUID.randomUUID();
    }

    public Notes(UUID identifier) {
        this.identifier = identifier;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getNoteContents() {
        return noteContents;
    }

    public void setNoteContents(String noteContents) {
        this.noteContents = noteContents;
    }
}
