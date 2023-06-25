package ru.skypro.lessons.springboot.weblibrary.securriti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("securityUserDetailsService")
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userRepository.findByUsername(username);
        return SecurityUserPrincipal.fromAuthUser(user);
    }
}