package com.example.bookStore.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String username;
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "UserLibrary",
            joinColumns = {
                @JoinColumn(name = "book_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "usr_id")
            }
    )
    private Set<Book> userLibrary = new HashSet<>();

    public User() {
    }

    public User(String name, String username, String password, Set<Role> role, Set<Book> userLibrary) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.userLibrary = userLibrary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public Set<Book> getUserLibrary() {
        return userLibrary;
    }

    public void setUserLibrary(Set<Book> userLibrary) {
        this.userLibrary = userLibrary;
    }
}
