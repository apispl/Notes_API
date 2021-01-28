package pl.pszczolkowski.notes_api.services;


import pl.pszczolkowski.notes_api.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

     Optional<List<Note>> getNotes();
     Optional<Note> findNoteInDb(long archiveId);
     void addNote(Note note);
     boolean checkNote(Note note);
     void prepareNote(Note note);
     void prepareNoteForRemoving(Note note);
     void creatingNewNote(Note noteNew, Note noteOld);
}
