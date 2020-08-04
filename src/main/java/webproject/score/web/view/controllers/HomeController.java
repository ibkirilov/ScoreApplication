package webproject.score.web.view.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String getIndex() {
        return "/home/index.html";
    }

    @GetMapping("/home")
    public String getHome() {
        return "/home/home";
    }
}
