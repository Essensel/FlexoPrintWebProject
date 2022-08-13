package ru.vishnyakov.flexoPrint.controllers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.vishnyakov.flexoPrint.controllers.beens.User;
import ru.vishnyakov.flexoPrint.controllers.services.UserService;

@Controller
public class SecurController {
    @Autowired
    private UserService userService;

    @GetMapping("/login/")
    public String showLogPage(ModelMap map){
        return "log";
    }
    @GetMapping("/login")
    public String showLoginPage(ModelMap map){
        return "login";
    }
    @RequestMapping(value = "/reg")
    public String register(ModelMap model){
        return "register";
    }

    @RequestMapping(value = "/adduser/", method = RequestMethod.POST)
    public String registerUser(User newUser) {
        if (newUser.getPassword().equals(newUser.getPasswordConf())) {
            this.userService.saveUser(newUser);
        } else {
            return "redirect:/register/";
        }
        return "redirect:/main";
    }

}
