package org.esprit.storeyc.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esprit.storeyc.entities.User;
import org.esprit.storeyc.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Management")
@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    /*@ModelAttribute est utilisé pour lier les données d'une requête HTTP aux paramètres d'une méthode de contrôleur.
     Cette annotation peut être utilisée pour extraire les données d'un formulaire HTML ou d'une requête GET,
      puis les utiliser dans la méthode de contrôleur pour effectuer une action.*/
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

}
