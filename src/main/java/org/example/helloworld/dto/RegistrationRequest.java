package org.example.helloworld.dto;

import java.util.Set; // <--- Необходимый импорт!

// DTO для данных, приходящих при регистрации пользователя
public class RegistrationRequest {

    private String username;
    private String email;
    private String password;
    private Set<String> roles; // <--- ИСПРАВЛЕНО: Теперь это Set<String>

    // --- Конструктор по умолчанию (требуется для Spring/Jackson) ---
    public RegistrationRequest() {}

    // --- Геттеры ---
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() { // <--- ИСПРАВЛЕНО: метод getRoles()
        return roles;
    }

    // --- Сеттеры ---
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<String> roles) { // <--- ИСПРАВЛЕНО: метод setRoles(Set<String>)
        this.roles = roles;
    }
}