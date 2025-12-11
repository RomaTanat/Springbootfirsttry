package org.example.helloworld.service;

import org.example.helloworld.model.Role;
import org.example.helloworld.model.User;
import org.example.helloworld.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service // Spring будет управлять этим сервисом
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // Инъекция репозитория
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Главный метод, который вызывает Spring Security при входе
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));

        // Преобразуем нашего User в UserDetails, который понимает Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // Хешированный пароль
                mapRolesToAuthorities(user.getRoles()) // Ваш метод для преобразования ролей
        );
    }

    /**
     * Вспомогательный метод для преобразования Set<Role> в Collection<? extends GrantedAuthority>
     * @param roles - набор ролей пользователя
     * @return - набор прав доступа (авторитетов) Spring Security
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        // Используем Stream API для преобразования Set<Role> в Set<SimpleGrantedAuthority>
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }
}