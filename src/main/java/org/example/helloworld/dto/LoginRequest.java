package org.example.helloworld.dto;

public class LoginRequest {
    private String username;
    private String password;

    // --- Конструктор по умолчанию (требуется для Spring/Jackson) ---
    public LoginRequest() {}

    // --- Геттеры (Обязательно для чтения данных Spring'ом) ---
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // --- Сеттеры (Обязательно для записи данных Spring'ом из JSON) ---
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}