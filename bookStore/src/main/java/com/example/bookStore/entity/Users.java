package com.example.bookStore.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usr")
public class Users implements UserDetails {
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
    private List<Book> userLibrary = new ArrayList<>();

    public Users() {
    }

    public Users(String name, String username, String password, Set<Role> role, List<Book> userLibrary) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.userLibrary = userLibrary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
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

    public void setRole(String role) {
        this.role = new HashSet<>();
        this.role.add(Role.valueOf(role));
    }

    public List<Book> getUserLibrary() {
        return userLibrary;
    }

    public void setUserLibrary(List<Book> userLibrary) {
        this.userLibrary = userLibrary;
    }

    public void addBookInLibrary(Book book) {
        this.userLibrary.add(book);
    }

    public boolean removeBookFromLibrary(Book book) {
        return this.userLibrary.remove(book);
    }

    public void clearLibrary() {
        this.userLibrary.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users user = (Users) o;
        return id.equals(user.id) &&
                name.equals(user.name) &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                role.equals(user.role) &&
                Objects.equals(userLibrary, user.userLibrary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, password, role);
    }
}
