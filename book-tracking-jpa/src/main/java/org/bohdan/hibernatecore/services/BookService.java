package org.bohdan.hibernatecore.services;

import jakarta.persistence.EntityNotFoundException;
import org.bohdan.hibernatecore.models.Book;
import org.bohdan.hibernatecore.models.Person;
import org.bohdan.hibernatecore.repositories.BookRepository;
import org.bohdan.hibernatecore.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Book> getBookList() {
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

        return person.getBooks();
    }

    @Transactional
    public void assignBookToReader(int readerId, int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        Person person = personRepository.findById(readerId).orElseThrow(() -> new EntityNotFoundException("Person not found"));

        book.setPerson(person);
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
}
