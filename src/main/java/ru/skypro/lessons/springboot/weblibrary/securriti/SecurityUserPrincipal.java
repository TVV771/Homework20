package ru.skypro.lessons.springboot.weblibrary.securriti;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.lessons.springboot.weblibrary.securriti.AuthUser;

import java.util.Collection;
import java.util.List;

public class SecurityUserPrincipal implements UserDetails {
    private final String username;
    private final String pasaword;
    private final List<GrantedAuthority> roles;

    public SecurityUserPrincipal(String username, String pasaword, List<GrantedAuthority> roles) {
        this.username = username;
        this.pasaword = pasaword;
        this.roles = roles;
    }

    public String getPasaword() {
        return pasaword;
    }

    public List<GrantedAuthority> getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return pasaword;
    }

    @Override
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

    public static UserDetails fromAuthUser(AuthUser user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPasaword())
                .authorities(user.getRole().getAuthority())
                .build();
    }

}