package ru.skypro.lessons.springboot.weblibrary.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        AuthUserDTO authUserDTO = AuthUserDTO.fromAuthUser(userRepository.findByUserName(userName));
        if (authUserDTO == null) {
            throw new UsernameNotFoundException(userName);
        }
        return new SecurityUserPrincipal(authUserDTO);
    }
}