package org.example.helloworld.model;

// Убрал импорт groovy.lang.GString; — он не нужен для JPA
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    // 1. ИСПРАВЛЕНИЕ: Добавляем поле email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Здесь хранится ХЕШ пароля!

    // Связь с Ролями: Многие ко многим
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    // --- 2. ИСПРАВЛЕНИЕ: КОНСТРУКТОРЫ ---
    // ОБЯЗАТЕЛЬНЫЙ КОНСТРУКТОР БЕЗ АРГУМЕНТОВ (для JPA)
    public User() {
    }

    // Параметризованный конструктор (для регистрации)
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    // --- ГЕТТЕРЫ И СЕТТЕРЫ ---
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // ГЕТТЕР И СЕТТЕР ДЛЯ EMAIL
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}