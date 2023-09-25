package org.bohdan.springcore.dao;

import org.bohdan.springcore.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeopleList() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, birth_year) VALUES (?, ?)",
                person.getFullName(),
                person.getBirthYear());
    }

    public Person getPersonById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE id=?",
                new BeanPropertyRowMapper<>(Person.class), id);
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return jdbcTemplate.query("SELECT p.full_name FROM Person p WHERE p.full_name=?",
                        new BeanPropertyRowMapper<>(Person.class), fullName)
                .stream()
                .findAny();
    }

    public void editPerson(Person person) {
        jdbcTemplate.update("UPDATE Person SET full_name=?, birth_year=? WHERE id=?",
                person.getFullName(), person.getBirthYear(), person.getId());
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public Optional<Person> getPersonByBookId(int bookId) {
        return jdbcTemplate.query(
                        "SELECT p.* FROM Person p" +
                                " INNER JOIN Book b ON p.id = b.person_id" +
                                " WHERE b.id=?",
                        new BeanPropertyRowMapper<>(Person.class), bookId)
                .stream()
                .findAny();
    }
}
