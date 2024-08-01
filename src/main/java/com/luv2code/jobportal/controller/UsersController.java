package com.luv2code.jobportal.controller;

import com.luv2code.jobportal.entity.Users;
import com.luv2code.jobportal.entity.UsersTypes;
import com.luv2code.jobportal.service.UsersService;
import com.luv2code.jobportal.service.UsersTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService,UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        List<UsersTypes> usersTypes = usersTypeService.findAll();
        model.addAttribute("getAllTypes",usersTypes);
        model.addAttribute("user",new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model){

        if(usersService.getUserByEmail(users.getEmail()).isPresent()) {

            model.addAttribute("error","Email already registered");
            List<UsersTypes> usersTypes = usersTypeService.findAll();
            model.addAttribute("getAllTypes",usersTypes);
            model.addAttribute("user",new Users());
            return "register";
        }

        this.usersService.registerNewUser(users);
        return "dashboard";
    }
}
