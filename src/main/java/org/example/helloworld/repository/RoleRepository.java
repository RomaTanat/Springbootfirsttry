package org.example.helloworld.repository;

import org.example.helloworld.model.Erole;
import org.example.helloworld.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Erole name);
}