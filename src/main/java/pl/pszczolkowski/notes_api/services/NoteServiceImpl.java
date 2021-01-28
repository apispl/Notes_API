package pl.pszczolkowski.notes_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pszczolkowski.notes_api.model.ArchivedNote;
import pl.pszczolkowski.notes_api.model.Note;
import pl.pszczolkowski.notes_api.repositories.NoteRepo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService{

    private NoteRepo noteRepo;

    @Autowired
    public NoteServiceImpl(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public Optional<List<Note>> getNotes(){
        return noteRepo.findByRemovedIsFalse();
    }

    public Optional<Note> findNoteInDb(long archiveId){
        return noteRepo.findByArchivedNoteIdAndRemovedIsFalse(archiveId);
    }

    public void addNote(Note note){
        noteRepo.save(note);
    }

    public boolean checkNote(Note note){
        return note.getTitle() == null || note.getContent() == null ||
                note.getTitle().length() == 0 || note.getContent().length() == 0;
    }

    public void prepareNote(Note note) {
        note.setArchivedNote(new ArchivedNote());
        note.setCreated(new Date());
        note.setModified(new Date());
        note.setVersion(1);
    }

    public void prepareNoteForRemoving(Note note) {
        note.setRemoved(true);
        note.setModified(new Date());
    }

    public void creatingNewNote(Note noteNew, Note noteOld) {
        noteNew.setCreated(noteOld.getCreated());
        noteNew.setVersion(noteOld.getVersion()+1);
        noteNew.setArchivedNote(noteOld.getArchivedNote());
        noteNew.setModified(new Date());
    }


}
