package ru.skypro.lessons.springboot.weblibrary.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.lessons.springboot.weblibrary.security.AuthUser;

public interface UserRepository extends JpaRepository<AuthUser, Integer> {

    AuthUser findByUsername(String username);
}