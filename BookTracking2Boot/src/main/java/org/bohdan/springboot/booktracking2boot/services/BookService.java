package org.bohdan.springboot.booktracking2boot.services;

import jakarta.persistence.EntityNotFoundException;
import org.bohdan.springboot.booktracking2boot.models.Book;
import org.bohdan.springboot.booktracking2boot.models.Person;
import org.bohdan.springboot.booktracking2boot.repositories.BookRepository;
import org.bohdan.springboot.booktracking2boot.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public Page<Book> getBookList(Integer pageNumber, Integer size, boolean sorted) {
        if (sorted) {
            return bookRepository.findAll(PageRequest
                    .of(pageNumber, size, Sort.by("year")));
        } else
            return bookRepository.findAll(PageRequest.of(pageNumber, size));
    }

    public List<Book> getSortedBookList(boolean sorted) {
        if (sorted) {
            return bookRepository.findAll(Sort.by("year"));
        } else
            return bookRepository.findAll();
    }

    @Transactional
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @PreAuthorize(
            "hasRole(T(org.bohdan.springboot.booktracking2boot.models.enums.Role).ADMIN.toString()) " +
                    "or hasRole(T(org.bohdan.springboot.booktracking2boot.models.enums.Role).LIBRARIAN.toString())"
    )
    @Transactional
    public void removeBook(int id) {
        bookRepository.deleteById(id);
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getPersonBooks(int personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        Hibernate.initialize(person.getBooks());

        Date currentTime = new Date();
        List<Book> books = person.getBooks();
        for (Book book : books) {
            long diff = currentTime.getTime() - book.getTakenAt().getTime();
            long millisecondsInADay = 24 * 60 * 60 * 1000; // 24 hours * 60 minutes * 60 seconds * 1000 milliseconds

            long diffInDays = diff / millisecondsInADay;
            book.setOverdue(diffInDays > 10);
        }

        return books;
    }

    @Transactional
    public void assignBookToReader(int readerId, int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        Person person = personRepository.findById(readerId).orElseThrow(() -> new EntityNotFoundException("Person not found"));

        book.setPerson(person);

        book.setTakenAt(new Date());

        person.getBooks().add(book);
    }

    @Transactional
    public void freeBook(int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Person person = book.getPerson();
        if (person != null) {
            person.getBooks().remove(book);
            book.setPerson(null);
        }
    }

    public List<Book> searchBookByName(String bookName) {
        return bookRepository.findByNameStartingWith(bookName);
    }
}
