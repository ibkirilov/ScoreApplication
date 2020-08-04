package webproject.score.web.view.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import webproject.score.services.models.UserServiceModel;
import webproject.score.services.services.UsersService;
import webproject.score.web.view.models.UserViewModel;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String getAdmin(Model model) {
        List<UserServiceModel> usersService = this.usersService.getAllUsers();
        List<UserViewModel> users = usersService
                .stream()
                .map(user -> this.modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        return "/admin/admin-panel";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}/remove-user")
    public String deleteUserRole(@PathVariable("id") String id) {
        this.usersService.deleteRoleById(id, "ROLE_USER");
        return "redirect:/admin/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}/remove-admin")
    public String deleteAdminRole(@PathVariable("id") String id) {
        this.usersService.deleteRoleById(id, "ROLE_ADMIN");
        return "redirect:/admin/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}/add-user")
    public String addUserRole(@PathVariable("id") String id) {
        this.usersService.addRoleById(id, "ROLE_USER");
        return "redirect:/admin/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}/add-admin")
    public String addAdminRole(@PathVariable("id") String id) {
        this.usersService.addRoleById(id, "ROLE_ADMIN");
        return "redirect:/admin/admin";
    }
}
