package ru.skypro.lessons.springboot.weblibrary.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SecurityUserPrincipal implements UserDetails {
    private String userName;
    private String password;
    private List<SecurityGrandAth> securityGrandAthList;

    public SecurityUserPrincipal(AuthUser user) {
        this.userName = user.getUsername();
        this.password =  user.getPassword();
        this.securityGrandAthList = user.getAuthorityList().stream()
                .map(SecurityGrandAth::new)
                .toList();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(securityGrandAthList);
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
}