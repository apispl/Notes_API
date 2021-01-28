package pl.pszczolkowski.notes_api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "notes")
public class Note {

    @Id
    @GeneratedValue
    long id;

    @NonNull
    private String title;
    @NonNull
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "archived_id")
    @JsonIgnore
    ArchivedNote archivedNote;

    private Date created;
    private Date modified;
    private boolean removed;
    private int version;

    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ArchivedNote getArchivedNote() {
        return archivedNote;
    }

    public void setArchivedNote(ArchivedNote archivedNote) {
        this.archivedNote = archivedNote;
    }
}
