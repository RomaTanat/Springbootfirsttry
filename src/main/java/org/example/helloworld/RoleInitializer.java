package org.example.helloworld;

import org.example.helloworld.model.Erole;
import org.example.helloworld.model.Role;
import org.example.helloworld.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Проверяем и создаем роль ROLE_USER
        if (roleRepository.findByName(Erole.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(Erole.ROLE_USER));
            System.out.println("--> Инициализирована роль: ROLE_USER");
        }

        // Проверяем и создаем роль ROLE_ADMIN
        if (roleRepository.findByName(Erole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(Erole.ROLE_ADMIN));
            System.out.println("--> Инициализирована роль: ROLE_ADMIN");
        }
    }
}