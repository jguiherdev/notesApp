package com.notes.note;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.notes.collection.Collection;

import lombok.Data;

@Data
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "title", nullable = false, unique = true)
    @Size(min = 6,max = 20, message = "{validation.name.size.invalid_size}")
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "description", nullable = false)
    @Size(min = 6,max = 600, message = "{validation.name.size.invalid_size}")
    private String description;

    @Column(name = "is_important", nullable = false)
    private Boolean isImportant;

    @Column(name = "is_pinned", nullable = false)
    private Boolean isPinned;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collectionId;

    //empty constructor
    public Note() {
    }

    //constructor with all fields
    public Note(Long id, String title, LocalDate date, String description, Boolean isImportant, Boolean isPinned,
            Boolean isCompleted, Collection collectionId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.isImportant = isImportant;
        this.isPinned = isPinned;
        this.isCompleted = isCompleted;
        this.collectionId = collectionId;
    }

    //constructor with all fields except id
    public Note(String title, LocalDate date, String description, Boolean isImportant, Boolean isPinned,
            Boolean isCompleted, Collection collectionId) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.isImportant = isImportant;
        this.isPinned = isPinned;
        this.isCompleted = isCompleted;
        this.collectionId = collectionId;
    }

    //constructor with all fields except id and collectionId
    public Note(String title, LocalDate date, String description, Boolean isImportant, Boolean isPinned,
            Boolean isCompleted) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.isImportant = isImportant;
        this.isPinned = isPinned;
        this.isCompleted = isCompleted;
    }

    //constructor with all fields except id, collectionId and isCompleted
    public Note(String title, LocalDate date, String description, Boolean isImportant, Boolean isPinned) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.isImportant = isImportant;
        this.isPinned = isPinned;
    }

    //constructor with all fields except id, collectionId, isCompleted and isPinned
    public Note(String title, LocalDate date, String description, Boolean isImportant) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.isImportant = isImportant;
    }

    //constructor with all fields except id, collectionId, isCompleted, isPinned and isImportant
    public Note(String title, LocalDate date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

}






