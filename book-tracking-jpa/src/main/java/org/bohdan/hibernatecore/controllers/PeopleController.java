package org.bohdan.hibernatecore.controllers;

import jakarta.validation.Valid;
import org.bohdan.hibernatecore.models.Person;
import org.bohdan.hibernatecore.services.BookService;
import org.bohdan.hibernatecore.services.PeopleService;
import org.bohdan.hibernatecore.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BookService bookService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String getPeopleList(Model model) {
        model.addAttribute("people", peopleService.getPeopleList());
        return "people/list";
    }

    @GetMapping("/new")
    public String createNewPersonPage(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("{id}")
    public String showPersonProfile(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("person", peopleService.getPersonById(id));
        model.addAttribute("books", bookService.getPersonBooks(id));
        return "people/profile";
    }

    @GetMapping("{id}/edit")
    public String showEditProfilePage(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("person", peopleService.getPersonById(id));
        return "people/edit";
    }

    @PutMapping
    public String editPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.editPerson(person);
        return "redirect:/people/" + person.getId();
    }

    @DeleteMapping("{id}")
    public String deletePerson(@PathVariable(name = "id") Integer id) {
        peopleService.deletePerson(id);
        return "redirect:/people";
    }
}
