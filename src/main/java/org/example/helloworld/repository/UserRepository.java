package org.example.helloworld.repository;

import org.example.helloworld.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 1. Метод для Spring Security (уже был)
    Optional<User> findByUsername(String username);

    // 2. Проверка уникальности Username (уже была)
    Boolean existsByUsername(String username);

    // 3. ✅ ДОБАВЛЕННЫЙ МЕТОД: Проверка уникальности Email (для регистрации)
    Boolean existsByEmail(String email);

    // 4. (Опционально) Метод для входа по Email, если потребуется
    Optional<User> findByEmail(String email);
}