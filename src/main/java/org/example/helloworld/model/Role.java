package org.example.helloworld.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Используем Enum для хранения имен ролей
    @Enumerated(EnumType.STRING)
    private Erole name;

    // 1. КОНСТРУКТОР БЕЗ АРГУМЕНТОВ (ОБЯЗАТЕЛЕН для JPA)
    public Role() {
    }

    // 2. КОНСТРУКТОР С АРГУМЕНТОМ (ОБЯЗАТЕЛЕН для RoleInitializer)
    public Role(Erole name) {
        this.name = name;
    }

    // 3. ГЕТТЕРЫ И СЕТТЕРЫ (ОБЯЗАТЕЛЬНЫ для JPA и логики)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Erole getName() {
        return name;
    }

    public void setName(Erole name) {
        this.name = name;
    }
}