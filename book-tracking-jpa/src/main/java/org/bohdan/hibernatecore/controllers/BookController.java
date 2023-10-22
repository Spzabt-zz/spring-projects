package org.bohdan.hibernatecore.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.bohdan.hibernatecore.models.Book;
import org.bohdan.hibernatecore.models.Person;
import org.bohdan.hibernatecore.services.BookService;
import org.bohdan.hibernatecore.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String bookList(@RequestParam(name = "page", required = false) Integer page,
                           @RequestParam(name = "books_per_page", required = false) Integer size,
                           @RequestParam(name = "sort_by_year", required = false) boolean sorted,
                           Model model) {
        if (page == null || size == null)
            model.addAttribute("books", bookService.getSortedBookList(sorted));
        else
            model.addAttribute("books", bookService.getBookList(page, size, sorted));

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

        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("{id}")
    public String getBookPage(@PathVariable(name = "id") Integer id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.getBookById(id));

        Optional<Person> personByBookId = peopleService.getPersonByBookId(id);

        if (personByBookId.isPresent())
            model.addAttribute("bookOwner", personByBookId.get());
        else
            model.addAttribute("people", peopleService.getPeopleList());

        return "books/book";
    }

    @GetMapping("{id}/edit")
    public String updateBookPage(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
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

        bookService.updateBook(id, book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable(name = "id") Integer id) {
        bookService.removeBook(id);
        return "redirect:/books";
    }

    @PatchMapping("assign-book")
    public String assignBookToPerson(@ModelAttribute("person") Person person, @RequestParam("bookId") Integer bookId)
            throws EntityNotFoundException {
        bookService.assignBookToReader(person.getId(), bookId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("free-book")
    public String freeBook(
            @ModelAttribute("person") Person person,
            @RequestParam("bookId") Integer bookId) throws EntityNotFoundException {
        bookService.freeBook(bookId);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("search")
    public String searchBookPage(@RequestParam(name = "book_name", required = false) String bookName, Model model) {
        List<Book> booksByName = new ArrayList<>();

        if (bookName != null)
            if (!bookName.isBlank())
                booksByName = bookService.searchBookByName(bookName);

        model.addAttribute("books", booksByName);

        return "books/search";
    }
}
