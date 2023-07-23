package ru.skypro.lessons.springboot.weblibrary.security;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Entity
@Table(name = "auth_user")
public class AuthUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;
    private boolean enabled;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role")
    private List <Authority> authorityList;
}