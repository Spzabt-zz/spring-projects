package org.bohdan.springcore.controllers;

import org.bohdan.springcore.dao.BookDao;
import org.bohdan.springcore.dao.PersonDao;
import org.bohdan.springcore.models.Book;
import org.bohdan.springcore.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String addNewBook(@ModelAttribute("book") Book book) {
        bookDao.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("{id}")
    public String getBookPage(@PathVariable(name = "id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDao.getBookById(id));
        model.addAttribute("people", personDao.getPeopleList());
        model.addAttribute("bookOwner", personDao.getPersonByBookId(id));
        return "books/book";
    }

    @GetMapping("{id}/edit")
    public String updateBookPage(@PathVariable(name = "id") int id, Model model) {
        model.addAttribute("book", bookDao.getBookById(id));
        return "books/edit";
    }

    @PatchMapping("{id}")
    public String updateBook(@ModelAttribute(name = "book") Book book, @PathVariable(name = "id") int id) {
        bookDao.updateBook(id, book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable(name = "id") int id) {
        bookDao.removeBook(id);
        return "redirect:/books";
    }

    @PostMapping("assign-book")
    public String assignBookToPerson(@ModelAttribute("person") Person person, @RequestParam("bookId") int bookId) {
        bookDao.assignBookToReader(person.getId(), bookId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("free-book")
    public String freeBook(
            @ModelAttribute("person") Person person,
            @RequestParam("bookId") int bookId)
    {
        bookDao.freeBook(bookId);
        return "redirect:/books/" + bookId;
    }
}
