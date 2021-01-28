package pl.pszczolkowski.notes_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.pszczolkowski.notes_api.model.ArchivedNote;
import pl.pszczolkowski.notes_api.model.Note;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void contextLoads(){
        assertThat(mockMvc).isNotNull();
        assertThat(objectMapper).isNotNull();
    }

    @Test
    public void shouldGetNotes() throws Exception {
       mockMvc.perform(get("/notes"))
               .andExpect(status().isOk());
    }

    @Test
    public void shouldNotGetNoteById() throws Exception {
        mockMvc.perform(get("/notes/{archiveId}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldAddNote() throws Exception {
        Note note1 = new Note();
        note1.setTitle("My first personal note");
        note1.setContent("By using the article a, " +
                "we’ve created a general statement, " +
                "implying that any cup of tea would taste good after any long day.");
        note1.setCreated(new Date());
        note1.setModified(new Date());
        note1.setRemoved(false);
        ArchivedNote archivedNote = new ArchivedNote();
        archivedNote.setId(2);
        note1.setArchivedNote(new ArchivedNote());
        note1.setVersion(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/notes")
                .content(objectMapper.writeValueAsString(note1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotAddNote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/notes")
                .content(objectMapper.writeValueAsString(new Note()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
}
