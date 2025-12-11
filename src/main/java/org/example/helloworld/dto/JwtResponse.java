package org.example.helloworld.dto;

// DTO для отправки JWT обратно клиенту
public class JwtResponse {

    private String token;
    private String type = "Bearer"; // Тип токена (стандарт JWT)
    private Long id;
    private String username;
    private String email;
    // Можно добавить список ролей (List<String> roles)

    // --- Конструктор для создания объекта после успешного входа ---
    public JwtResponse(String accessToken, Long id, String username, String email) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // --- Геттеры и Сеттеры для полей ---
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    // ... (Геттеры/сеттеры для id, username, email)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}