package com.notes.note;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.collection.CollectionRepository;

@Service
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CollectionRepository collectionRepository;
    
   public Iterable<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note save(NoteDTO noteDTO) {
        Note note = new Note(
            noteDTO.getTitle(),
            noteDTO.getDate(),
            noteDTO.getDescription(),
            noteDTO.getIsImportant(),
            noteDTO.getIsPinned(),
            noteDTO.getIsCompleted(),
            collectionRepository.findById(noteDTO.getCollectionId()).get()
        );
        return noteRepository.save(note);
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id).get();
    }
    
    public Note updateNote(Long id, NoteDTO noteDTO) {
        Note noteToUpdate = noteRepository.findById(id).get();
        noteToUpdate.setTitle(noteDTO.getTitle());
        noteToUpdate.setDescription(noteDTO.getDescription());
        return noteRepository.save(noteToUpdate);
    }

    public Note pinNote(Long id) {
        Note noteToPin = noteRepository.findById(id).get();
        noteToPin.setIsPinned(!noteToPin.getIsPinned());
        return noteRepository.save(noteToPin);
    }

    public Note setNoteAsCompleted(Long id) {
        Note noteToSetAsCompleted = noteRepository.findById(id).get();
        noteToSetAsCompleted.setIsCompleted(!noteToSetAsCompleted.getIsCompleted());
        return noteRepository.save(noteToSetAsCompleted);
    }

    public Note setNoteAsImportant(Long id) {
        Note noteToSetAsImportant = noteRepository.findById(id).get();
        noteToSetAsImportant.setIsImportant(!noteToSetAsImportant.getIsImportant());
        return noteRepository.save(noteToSetAsImportant);
    }
    
    public Iterable<Note> findAllByAppUserId(Long id) {
        return noteRepository.findAllByAppUserId(id);
    }

    public Iterable<Note> findNotesByCollectionId(Long id) {
        return noteRepository.findNotesByCollectionId(id);
    }
    
}
