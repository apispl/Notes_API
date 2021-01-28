package pl.pszczolkowski.notes_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import pl.pszczolkowski.notes_api.model.ArchivedNote;
import pl.pszczolkowski.notes_api.model.Note;
import pl.pszczolkowski.notes_api.services.NoteService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/notes")
@RestController
@CrossOrigin("*")
public class NotesAPI {

    private NoteService noteService;

    @Autowired
    public NotesAPI(NoteService noteService) {
        this.noteService = noteService;
        Note note1 = new Note();
        note1.setTitle("My first personal note");
        note1.setContent("By using the article a, " +
                "we’ve created a general statement, " +
                "implying that any cup of tea would taste good after any long day.");
        note1.setCreated(new Date());
        note1.setModified(new Date());
        note1.setRemoved(false);
        note1.setArchivedNote(new ArchivedNote());
        note1.setVersion(1);

        Note note2 = new Note();
        note2.setTitle("My second personal note");
        note2.setContent("For example, your friend might ask, " +
                "“Are you going to the party this weekend?” " +
                "The definite article tells you that your friend is referring " +
                "to a specific party that both of you know about.");
        note2.setCreated(new Date());
        note2.setModified(new Date());
        note2.setArchivedNote(new ArchivedNote());
        note2.setRemoved(false);
        note2.setVersion(1);

        noteService.addNote(note1);
        noteService.addNote(note2);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes(){
        Optional<List<Note>> optionalNotes = noteService.getNotes();
        return optionalNotes
                .map(notes -> new ResponseEntity<>(notes, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{archiveId}")
    public ResponseEntity<Note> getNoteById(@PathVariable @NonNull long archiveId){
        Optional<Note> optionalNote = noteService.findNoteInDb(archiveId);
        return optionalNote
                .map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody @NonNull Note note) {
        if (noteService.checkNote(note)){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        noteService.prepareNote(note);
        noteService.addNote(note);
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @PutMapping("/{archiveId}")
    public ResponseEntity<Note> updateNoteById(@PathVariable @NonNull long archiveId, @RequestBody @NonNull Note note){
        Optional<Note> optionalNote = noteService.findNoteInDb(archiveId);
        if (optionalNote.isPresent()){
            Note noteTemp = optionalNote.get();
            noteService.prepareNoteForRemoving(noteTemp);

            //New note checking and saving
            if (!noteService.checkNote(note))
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            noteService.creatingNewNote(note, noteTemp);
            noteService.addNote(note);
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{archiveId}")
    public ResponseEntity<Note> deleteVehicleById(@PathVariable @NonNull long archiveId){
        Optional<Note> optionalNote = noteService.findNoteInDb(archiveId);
        if (optionalNote.isPresent()){
            Note noteTemp = optionalNote.get();
            noteService.prepareNoteForRemoving(noteTemp);
            noteService.addNote(noteTemp);
            return new ResponseEntity<>( HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
