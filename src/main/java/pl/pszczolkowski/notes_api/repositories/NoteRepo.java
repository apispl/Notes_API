package pl.pszczolkowski.notes_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pszczolkowski.notes_api.model.Note;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {
    Optional<List<Note>> findByRemovedIsFalse();
    Optional<Note> findByArchivedNoteIdAndRemovedIsFalse(long id);
    Optional<List<Note>> findByArchivedNoteId(long archiveId);
}
