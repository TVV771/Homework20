package ru.skypro.lessons.springboot.weblibrary.security;

import org.springframework.security.core.GrantedAuthority;

public class SecurityGrandAth implements GrantedAuthority {
    private String role;

    public SecurityGrandAth(Authority authority) {
        {
            this.role = authority.getRole();
        }}
    @Override
    public String getAuthority () {
        return String.valueOf(this.role);
    }

}