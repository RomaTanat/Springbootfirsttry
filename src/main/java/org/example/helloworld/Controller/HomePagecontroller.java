package org.example.helloworld.Controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePagecontroller {
    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("title", "Home Page");
        model.addAttribute("message", "Welcome to the Home Page!");
        return "homePage";
    }
 @GetMapping("/about")
    public String aboutpage(Model model) {
        model.addAttribute("title", "About Page");
        model.addAttribute("message", "This is the About Page.");
        return "aboutPage";
    }
 @GetMapping("/contact")
    public String contactpage(Model model) {
        model.addAttribute("title", "Contact Page");
        model.addAttribute("message", "This is the Contact Page.");
        return "contactPage";
    }
}
