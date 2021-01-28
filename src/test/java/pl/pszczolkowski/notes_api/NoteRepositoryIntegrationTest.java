package pl.pszczolkowski.notes_api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pszczolkowski.notes_api.model.ArchivedNote;
import pl.pszczolkowski.notes_api.model.Note;
import pl.pszczolkowski.notes_api.repositories.NoteRepo;
import pl.pszczolkowski.notes_api.services.NoteService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteRepositoryIntegrationTest {

    @Autowired
    private NoteService noteService;

    @MockBean
    private NoteRepo noteRepo;

    List<Note> notes = new LinkedList<>();
    Note note1 = new Note();
    Note note2 = new Note();

    public NoteRepositoryIntegrationTest() {
        note1.setTitle("My first personal note");
        note1.setContent("By using the article a, " +
                "we’ve created a general statement, " +
                "implying that any cup of tea would taste good after any long day.");
        note1.setCreated(new Date());
        note1.setModified(new Date());
        note1.setRemoved(false);
        note1.setArchivedNote(new ArchivedNote());
        note1.setVersion(1);

        note2.setTitle("My second personal note");
        note2.setContent("For example, your friend might ask, " +
                "“Are you going to the party this weekend?” " +
                "The definite article tells you that your friend is referring " +
                "to a specific party that both of you know about.");
        note2.setCreated(new Date());
        note2.setModified(new Date());
        note2.setArchivedNote(new ArchivedNote());
        note2.setRemoved(true);
        note2.setVersion(1);

        notes.add(note1);
        notes.add(note2);
    }


    @Test
    public void shouldgetNotes() {

        when(noteRepo.findByRemovedIsFalse()).thenReturn(java.util.Optional.of((notes)));
        assertEquals(2, noteService.getNotes().get().size());
    }

    @Test
    public void shouldgetNoteFirst() {

        when(noteRepo.findByRemovedIsFalse()).thenReturn(java.util.Optional.of((notes)));
        assertEquals(note1.getTitle(), noteService.getNotes().get().get(0).getTitle());
    }

    @Test
    public void getNoteById() {

        when(noteRepo.findByArchivedNoteIdAndRemovedIsFalse(2)).thenReturn(Optional.of(note1));
        assertEquals(note1.getContent(), noteService.findNoteInDb(2).get().getContent());
    }
}
