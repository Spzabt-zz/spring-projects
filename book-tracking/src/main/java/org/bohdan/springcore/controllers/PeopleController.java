package org.bohdan.springcore.controllers;

import jakarta.validation.Valid;
import org.bohdan.springcore.dao.BookDao;
import org.bohdan.springcore.dao.PersonDao;
import org.bohdan.springcore.models.Person;
import org.bohdan.springcore.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;
    private final BookDao bookDao;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDao personDao, BookDao bookDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.bookDao = bookDao;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String getPeopleList(Model model) {
        model.addAttribute("people", personDao.getPeopleList());
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

        personDao.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("{id}")
    public String showPersonProfile(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("person", personDao.getPersonById(id));
        model.addAttribute("books", bookDao.getPersonBooks(id));
        return "people/profile";
    }

    @GetMapping("{id}/edit")
    public String showEditProfilePage(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("person", personDao.getPersonById(id));
        return "people/edit";
    }

    @PutMapping
    public String editPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        personDao.editPerson(person);
        return "redirect:/people/" + person.getId();
    }

    @DeleteMapping("{id}")
    public String deletePerson(@PathVariable(name = "id") Integer id) {
        personDao.deletePerson(id);
        return "redirect:/people";
    }
}
