package org.bohdan.springboot.booktracking2boot.controllers;

import jakarta.validation.Valid;
import org.bohdan.springboot.booktracking2boot.models.Person;
import org.bohdan.springboot.booktracking2boot.models.enums.Role;
import org.bohdan.springboot.booktracking2boot.services.RegistrationService;
import org.bohdan.springboot.booktracking2boot.util.converters.SpecificRolesToArray;
import org.bohdan.springboot.booktracking2boot.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("roles", SpecificRolesToArray.getRoleArray());

        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(
            @ModelAttribute("person") @Valid Person person,
            BindingResult bindingResult,
            @RequestParam(name = "role") Role role,
            Model model
    ) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", SpecificRolesToArray.getRoleArray());
            return "auth/registration";
        }

        registrationService.register(person, role);

        return "redirect:/auth/login";
    }
}
