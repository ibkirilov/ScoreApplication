package webproject.score.web.view.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webproject.score.services.models.RegisterUserServiceModel;
import webproject.score.services.models.TeamCreateServiceModel;
import webproject.score.services.services.AuthService;
import webproject.score.services.services.CloudinaryService;
import webproject.score.services.services.TeamsService;
import webproject.score.web.view.models.RegisterUserViewModel;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class LoginRegisterController {
    private final ModelMapper mapper;
    private final AuthService authService;
    private final TeamsService teamsService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public LoginRegisterController(ModelMapper mapper, AuthService authService, TeamsService teamsService, CloudinaryService cloudinaryService) {
        this.mapper = mapper;
        this.authService = authService;
        this.teamsService = teamsService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/login")
    public String getLoginForm() {
        String path = "auth/login.html";
        path = changePathToHomeIfUserIsLoggedIn(path);
        return path;
    }

//    @PostMapping("/login-error")
//    public ModelAndView onLoginError(@ModelAttribute("username") String username) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("error", "bad.credentials");
//        modelAndView.addObject("username", username);
//
//        modelAndView.setViewName("/auth/login");
//        return modelAndView;
//    }


    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        if (!model.containsAttribute("registerUserViewModel")) {
            model.addAttribute("registerUserViewModel", new RegisterUserViewModel());
        }
        if (!model.containsAttribute("passwordsNotEqual")) {
            model.addAttribute("passwordsNotEqual", false);
        }

        return "/auth/register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid @ModelAttribute("registerUserViewModel") RegisterUserViewModel registerUserViewModel
            , BindingResult bindingResult
            , ModelAndView modelAndView
            , RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerUserViewModel", registerUserViewModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerUserViewModel", bindingResult);
            modelAndView.setViewName("redirect:/users/register");
            if (!registerUserViewModel.getPassword().equals(registerUserViewModel.getConfirmPassword())) {
                redirectAttributes.addFlashAttribute("passwordsNotEqual", true);
            }
            return modelAndView;
        }

        RegisterUserServiceModel registerUserServiceModel = mapper.map(registerUserViewModel, RegisterUserServiceModel.class);
        this.authService.register(registerUserServiceModel);

        try {
            TeamCreateServiceModel teamCreateServiceModel = mapper.map(registerUserViewModel, TeamCreateServiceModel.class);
            teamCreateServiceModel.setLogoLink(this.cloudinaryService.uploadFile(registerUserViewModel.getLogoLink()));
            this.teamsService.create(teamCreateServiceModel, registerUserServiceModel.getUsername());
        } catch (Exception e) {
            modelAndView.setViewName("redirect:/users/register");
        }

        modelAndView.setViewName("redirect:/users/login");

        return modelAndView;
    }

    private String changePathToHomeIfUserIsLoggedIn(String path) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return path;
    }
}
