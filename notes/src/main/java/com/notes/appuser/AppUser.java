package com.notes.appuser;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.notes.collection.Collection;

import lombok.Data;

@Data
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nick", unique = true, nullable = false)
    @Size(min = 6, max = 20, message = "invalid nick size")
    private String nick;

    @Column(name = "mail", unique = true, nullable = false)
    @Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String mail;

    @Column(name = "password", nullable = false)
    @Size(min = 6, message = "Password not valid or not matched, check it again")
    private String password;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "avatar")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)", message = "invalid avatar extension")
    private String avatar;

    @OneToMany(mappedBy = "appUserId", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Collection> collections;

    public AppUser(String nick, String mail, String password, String phone, String avatar) {
        this.nick = nick;
        this.mail = mail;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
    }

    public AppUser() {
    }

}
