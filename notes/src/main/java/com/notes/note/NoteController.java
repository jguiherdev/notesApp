package com.notes.note;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {

  @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> findAll() {
        List<Note> notes = noteService.findAll();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Note> save(@RequestBody NoteDTO noteDTO) {
        Note savedNote = noteService.save(noteDTO);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteById(@PathVariable Long id) {
        noteService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

   @GetMapping("/{id}")
    public ResponseEntity<Note> findById(@PathVariable Long id) {
        Note note = noteService.findById(id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
        Note updatedNote = noteService.updateNote(id, noteDTO);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @PutMapping("pin/{id}")
    public ResponseEntity<Note> pin(@PathVariable Long id) {
        Note updatedNote = noteService.pinNote(id);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @PutMapping("relevance/{id}")
    public ResponseEntity<Note> relevance(@PathVariable Long id) {
        Note updatedNote = noteService.setNoteAsImportant(id);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @PutMapping("archive/{id}")
    public ResponseEntity<Note> archive(@PathVariable Long id) {
        Note updatedNote = noteService.setNoteAsCompleted(id);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @GetMapping("/app-user/{id}")
    public ResponseEntity<List<Note>> findAllByAppUserId(@PathVariable Long id) {
        List<Note> notes = noteService.findAllByAppUserId(id);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/collection/{id}")
    public ResponseEntity<List<Note>> findNotesByCollectionId(@PathVariable Long id){
        List<Note> notes = noteService.findNotesByCollectionId(id);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
}
