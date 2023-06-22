package com.notes.collection;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.notes.appuser.AppUser;
import com.notes.note.Note;

import lombok.Data;

@Data
@Entity
@Table(name = "collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUserId;

    @OneToMany(mappedBy = "collectionId", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Note> notes;

    public Collection() {
    }

    public Collection(Long id, String name, AppUser appUserId) {
        this.id = id;
        this.name = name;
    }

    public Collection(String name) {
        this.name = name;
    }

    public Collection(String name, AppUser appUserId) {
        this.name = name;
        this.appUserId = appUserId;
    }

}
