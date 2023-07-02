package ru.skypro.lessons.springboot.weblibrary.securriti;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role {
    ADMIN("ADMIN"), USER("USER");
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public List<GrantedAuthority> getAuthority() {
        return List.of(new SimpleGrantedAuthority(getRole()));
    }
}