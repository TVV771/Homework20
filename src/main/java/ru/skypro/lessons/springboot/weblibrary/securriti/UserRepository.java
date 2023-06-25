package ru.skypro.lessons.springboot.weblibrary.securriti;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.lessons.springboot.weblibrary.securriti.AuthUser;

public interface UserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findByUsername(String username);
}