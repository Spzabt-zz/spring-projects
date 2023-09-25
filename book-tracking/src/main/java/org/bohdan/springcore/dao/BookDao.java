package org.bohdan.springcore.dao;

import org.bohdan.springcore.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBookList() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void updateBook(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void removeBook(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public Book getBookById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM Book b WHERE b.id=?", new BeanPropertyRowMapper<>(Book.class), id);
    }

    public List<Book> getPersonBooks(int personId) {
        return jdbcTemplate.query("SELECT b.name, b.author, b.year FROM Book b WHERE b.person_id = ?",
                new BeanPropertyRowMapper<>(Book.class), personId);
    }

    public void assignBookToReader(int readerId, int bookId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", readerId, bookId);
    }

    public void freeBook(int bookId) {
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE id=?", bookId);
    }
}
