package org.example.helloworld.config;

import org.example.helloworld.model.Erole;
import org.example.helloworld.model.Role;
import org.example.helloworld.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            for (Erole role : Erole.values()) {
                if (roleRepository.findByName(role).isEmpty()) {
                    roleRepository.save(new Role(role));
                    System.out.println("Role initialized: " + role);
                }
            }
        };
    }
}
