package com.notes.note;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long>{

    @Query("SELECT n FROM Note n, Collection c WHERE c.appUserId.id = :id")
    Iterable<Note> findAllByAppUserId(Long id);

    @Query("SELECT n FROM Note n WHERE n.collectionId.id = :id")
    Iterable<Note> findNotesByCollectionId(Long id);
   
}
