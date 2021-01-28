package pl.pszczolkowski.notes_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import pl.pszczolkowski.notes_api.model.Note;
import pl.pszczolkowski.notes_api.repositories.NoteRepo;

import java.util.List;
import java.util.Optional;

@RequestMapping("/archive")
@RestController
@CrossOrigin("*")
public class ArchivedNoteAPI {

    private NoteRepo noteRepo;

    @Autowired
    public ArchivedNoteAPI(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotesArchive(){
        List<Note> notes = noteRepo.findAll();
        if (!notes.isEmpty()){
            return new ResponseEntity<>(notes, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{archiveId}")
    public ResponseEntity<List<Note>> getNoteArchiveById(@PathVariable @NonNull long archiveId){
        Optional<List<Note>> optionalNote = noteRepo.findByArchivedNoteId(archiveId);
        return optionalNote
                .map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
