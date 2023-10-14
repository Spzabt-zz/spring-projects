package org.bohdan.hibernatecore.dao;

import org.bohdan.hibernatecore.models.Book;
import org.bohdan.hibernatecore.models.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> getBookList() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT b from Book b", Book.class).getResultList();
    }

    @Transactional
    public void addBook(Book book) {
        Session session = sessionFactory.getCurrentSession();

        session.persist(book);
    }

    @Transactional
    public void updateBook(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();

        Book bookToUpdate = session.get(Book.class, id);

        bookToUpdate.setName(book.getName());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setYear(book.getYear());
    }

    @Transactional
    public void removeBook(int id) {
        Session session = sessionFactory.getCurrentSession();

        session.remove(session.get(Book.class, id));
    }

    @Transactional(readOnly = true)
    public Book getBookById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Book.class, id);
    }

    @Transactional(readOnly = true)
    public List<Book> getPersonBooks(int personId) {
        Session session = sessionFactory.getCurrentSession();

        Person person = session.get(Person.class, personId);

        Hibernate.initialize(person.getBooks());

        return person.getBooks();
    }

    @Transactional
    public void assignBookToReader(int readerId, int bookId) {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, bookId);
        Person person = session.get(Person.class, readerId);

        book.setPerson(person);
        person.getBooks().add(book);
    }

    @Transactional
    public void freeBook(int bookId) {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, bookId);

        book.getPerson().getBooks().remove(book);
        book.setPerson(null);
    }
}
