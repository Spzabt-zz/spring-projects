package org.bohdan.springcore.controllers;

import org.bohdan.springcore.dao.BookDao;
import org.bohdan.springcore.dao.PersonDao;
import org.bohdan.springcore.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;
    private final BookDao bookDao;

    @Autowired
    public PeopleController(PersonDao personDao, BookDao bookDao) {
        this.personDao = personDao;
        this.bookDao = bookDao;
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
    public String createNewPerson(@ModelAttribute("person") Person person) {
        personDao.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("{id}")
    public String showPersonProfile(@PathVariable(name = "id") int id, Model model) {
        model.addAttribute("person", personDao.getPersonById(id));
        model.addAttribute("books", bookDao.getPersonBooks(id));
        return "people/profile";
    }

    @GetMapping("{id}/edit")
    public String showEditProfilePage(@PathVariable(name = "id") int id, Model model) {
        model.addAttribute("person", personDao.getPersonById(id));
        return "people/edit";
    }

    @PatchMapping
    public String editPerson(@ModelAttribute("person") Person person) {
        personDao.editPerson(person);
        return "redirect:/people/" + person.getId();
    }

    @DeleteMapping("{id}")
    public String deletePerson(@PathVariable(name = "id") int id) {
        personDao.deletePerson(id);
        return "redirect:/people";
    }
}
