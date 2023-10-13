package org.bohdan.hibernatecore.dao;

import org.bohdan.hibernatecore.models.Book;
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

    public void addBook(Book book) {
        /*jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());*/
    }

    public void updateBook(int id, Book book) {
        /*jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);*/
    }

    public void removeBook(int id) {
        /*jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);*/
    }

    public Book getBookById(int id) {
        return null;
        /*return jdbcTemplate.queryForObject(
                "SELECT * FROM Book b WHERE b.id=?", new BeanPropertyRowMapper<>(Book.class), id);*/
    }

    public List<Book> getPersonBooks(int personId) {
        return null;
        /*return jdbcTemplate.query("SELECT b.name, b.author, b.year FROM Book b WHERE b.person_id = ?",
                new BeanPropertyRowMapper<>(Book.class), personId);*/
    }

    public void assignBookToReader(int readerId, int bookId) {
        /*jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", readerId, bookId);*/
    }

    public void freeBook(int bookId) {
        /*jdbcTemplate.update("UPDATE Book SET person_id=null WHERE id=?", bookId);*/
    }
}
