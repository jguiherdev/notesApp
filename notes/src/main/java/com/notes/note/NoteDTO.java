package com.notes.note;

import java.time.LocalDate;

public class NoteDTO {

    private String title;
    private LocalDate date;
    private String description;
    private Long collectionId;
    private Boolean isImportant;
    private Boolean isPinned;
    private Boolean isCompleted;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getCollectionId() {
        return collectionId;
    }
    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Boolean getIsImportant() {
        return isImportant;
    }
    public void setIsImportant(Boolean isImportant) {
        this.isImportant = isImportant;
    }
    public Boolean getIsPinned() {
        return isPinned;
    }
    public void setIsPinned(Boolean isPinned) {
        this.isPinned = isPinned;
    }
    public Boolean getIsCompleted() {
        return isCompleted;
    }
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    


    
}
