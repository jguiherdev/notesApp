package com.notes.note;

import java.util.ArrayList;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<Iterable<Note>> findAll() {
        Iterable<Note> notes = noteService.findAll();
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
    public ResponseEntity<Iterable<Note>> findAllByAppUserId(@PathVariable Long id) {
        Iterable<Note> notes = noteService.findAllByAppUserId(id);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/collection/{id}")
    public ResponseEntity<Iterable<Note>> findNotesByCollectionId(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Iterable<Note> notes = noteService.findNotesByCollectionId(id);
        List<Note> allNotes = new ArrayList<>();

        notes.forEach(allNotes::add);

        Comparator<Note> noteComparator = (note1, note2) -> {
            // Comparaciones según las condiciones dadas
            if (note1.getIsPinned() && note1.getIsImportant() && !(note2.getIsPinned() && note2.getIsImportant())) {
                return -1;
            } else if (note2.getIsPinned() && note2.getIsImportant() && !(note1.getIsPinned() && note1.getIsImportant())) {
                return 1;
            } else if (note1.getIsPinned() && !note2.getIsPinned()) {
                return -1;
            } else if (note2.getIsPinned() && !note1.getIsPinned()) {
                return 1;
            } else if (note1.getIsImportant() && !note2.getIsImportant()) {
                return -1;
            } else if (note2.getIsImportant() && !note1.getIsImportant()) {
                return 1;
            } else if (!note1.getIsCompleted() && note2.getIsCompleted()) {
                return -1;
            } else if (!note2.getIsCompleted() && note1.getIsCompleted()) {
                return 1;
            } else {
                return 0;
            }
        };

        allNotes.sort(noteComparator);

        int start = page * size;
        int end = Math.min(start + size, allNotes.size());
        List<Note> paginatedList = allNotes.subList(start, end);

        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<Integer> findAllAndCountByCollectionId(
        @PathVariable Long id
    ) {
    Iterable<Note> notes = noteService.findNotesByCollectionId(id);
    List<Note> allNotes = new ArrayList<>();
    for (Note note : notes) {
        allNotes.add(note);
    }
    return new ResponseEntity<>(allNotes.size(), HttpStatus.OK);
    }
}
