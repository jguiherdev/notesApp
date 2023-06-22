package com.notes.note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepository extends JpaRepository<Note, Long>{

    @Query("SELECT n FROM Note n, Collection c WHERE c.appUserId = :id")
    List<Note> findAllByAppUserId(Long id);
   
}
