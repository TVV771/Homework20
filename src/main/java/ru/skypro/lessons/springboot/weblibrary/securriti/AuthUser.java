package ru.skypro.lessons.springboot.weblibrary.securriti;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_user")
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String pasaword;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;
    public AuthUser() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasaword() {
        return pasaword;
    }

    public void setPasaword(String pasaword) {
        this.pasaword = pasaword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}