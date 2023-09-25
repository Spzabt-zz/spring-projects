package org.bohdan.springcore.controllers;

import jakarta.validation.Valid;
import org.bohdan.springcore.dao.BookDao;
import org.bohdan.springcore.dao.PersonDao;
import org.bohdan.springcore.models.Book;
import org.bohdan.springcore.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String bookList(Model model) {
        model.addAttribute("books", bookDao.getBookList());
        return "books/list";
    }

    @GetMapping("/new")
    public String bookCreationPage(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String addNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDao.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("{id}")
    public String getBookPage(@PathVariable(name = "id") Integer id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDao.getBookById(id));

        Optional<Person> personByBookId = personDao.getPersonByBookId(id);

        if (personByBookId.isPresent())
            model.addAttribute("bookOwner", personByBookId.get());
        else
            model.addAttribute("people", personDao.getPeopleList());

        return "books/book";
    }

    @GetMapping("{id}/edit")
    public String updateBookPage(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("book", bookDao.getBookById(id));
        return "books/edit";
    }

    @PutMapping("{id}")
    public String updateBook(
            @ModelAttribute(name = "book") @Valid Book book,
            BindingResult bindingResult,
            @PathVariable(name = "id") Integer id
    ) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDao.updateBook(id, book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable(name = "id") Integer id) {
        bookDao.removeBook(id);
        return "redirect:/books";
    }

    @PatchMapping("assign-book")
    public String assignBookToPerson(@ModelAttribute("person") Person person, @RequestParam("bookId") Integer bookId) {
        bookDao.assignBookToReader(person.getId(), bookId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("free-book")
    public String freeBook(
            @ModelAttribute("person") Person person,
            @RequestParam("bookId") Integer bookId) {
        bookDao.freeBook(bookId);
        return "redirect:/books/" + bookId;
    }
}
