package pl.pszczolkowski.notes_api.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "archived_note")
public class ArchivedNote {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany
    @JoinColumn(name = "archived_id")
    private List<Note> notes;

    public ArchivedNote() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
