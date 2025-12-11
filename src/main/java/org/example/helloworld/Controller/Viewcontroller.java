package org.example.helloworld.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Viewcontroller {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Возвращает шаблон src/main/resources/templates/login.html
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Возвращает шаблон src/main/resources/templates/register.html
    }

    // Добавим простой защищенный путь для проверки
    @GetMapping("/dashboard")
    public String showDashboard() {
        // Эта страница будет доступна только при наличии JWT в заголовке запроса,
        // если вы настроите AJAX-запрос с токеном.
        return "dashboard"; // Предполагается, что есть шаблон dashboard.html
    }
}