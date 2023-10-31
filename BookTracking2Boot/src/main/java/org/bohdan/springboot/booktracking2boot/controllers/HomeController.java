package org.bohdan.springboot.booktracking2boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String homePage() {
        return "test";
    }

    @GetMapping("admin")
    public String adminPage() {
        return "admin";
    }
}
