package org.example.helloworld.Controller;

import org.example.helloworld.dto.LoginRequest;
import org.example.helloworld.dto.RegistrationRequest;
import org.example.helloworld.dto.JwtResponse;
import org.example.helloworld.dto.MessageResponse;
import org.example.helloworld.jwt.JwtUtils;
import org.example.helloworld.model.User;
import org.example.helloworld.model.Role;
import org.example.helloworld.model.Erole;
import org.example.helloworld.repository.UserRepository;
import org.example.helloworld.repository.RoleRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController // Важно: для API, возвращаем JSON
@RequestMapping("/api/auth")
public class Authcontroller {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    // Инжектирование зависимостей через конструктор (лучшая практика)
    public Authcontroller(AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    // --------------------------------------------------------------------------------
    // 1. ЛОГИКА ВХОДА (/api/auth/login)
    // --------------------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest,
            jakarta.servlet.http.HttpServletResponse response) {
        try {
            // 1. Процесс Аутентификации: Проверка логина и пароля
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // 2. Установка аутентифицированного объекта в SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Генерация JWT-токена
            String jwt = jwtUtils.generateJwtToken(authentication);

            // 3.1. Установка JWT в HttpOnly Cookie (для поддержки браузерных переходов)
            jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("jwtToken", jwt);
            cookie.setHttpOnly(true); // Защита от XSS (JS не может читать этот куки, но отправляет его)
            cookie.setSecure(false); // Установите true для HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // 1 день
            response.addCookie(cookie);

            // 4. Получение деталей пользователя для ответа
            User userDetails = (User) authentication.getPrincipal();

            // 5. Возврат токена и данных пользователя
            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail()));
        } catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity
                    .status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Ошибка входа: Неверные данные"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Ошибка сервера: " + e.getMessage()));
        }
    }

    // --------------------------------------------------------------------------------
    // 2. ЛОГИКА РЕГИСТРАЦИИ (/api/auth/register)
    // --------------------------------------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest signUpRequest) {

        // 1. Проверка наличия Username
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    // ИСПРАВЛЕНИЕ: Возвращаем JSON-объект MessageResponse
                    .body(new MessageResponse("Ошибка: Имя пользователя уже занято!"));
        }

        // 2. Проверка наличия Email (ЭТА ПРОВЕРКА ОТСУТСТВОВАЛА)
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    // ИСПРАВЛЕНИЕ: Возвращаем JSON-объект MessageResponse
                    .body(new MessageResponse("Ошибка: Email уже занят!"));
        }

        // 3. Создание нового объекта User (оставшийся код без изменений)
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        // 4. Назначение ролей
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Erole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_USER' is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(Erole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_ADMIN' is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(Erole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_USER' is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);

        // 5. Сохранение пользователя
        userRepository.save(user);

        // ИСПРАВЛЕНИЕ: Возвращаем JSON-объект MessageResponse для успеха
        return ResponseEntity.ok(
                new MessageResponse("Пользователь успешно зарегистрирован!"));
    }
}