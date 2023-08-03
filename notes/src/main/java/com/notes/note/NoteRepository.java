package com.notes.note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepository extends JpaRepository<Note, Long>{

    @Query("SELECT n FROM Note n, Collection c WHERE c.appUserId.id = :id")
    List<Note> findAllByAppUserId(Long id);

    @Query("SELECT n FROM Note n WHERE n.collectionId.id = :id")
    List<Note> findNotesByCollectionId(Long id);
   
}
