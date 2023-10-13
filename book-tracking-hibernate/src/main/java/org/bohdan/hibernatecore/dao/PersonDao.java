package org.bohdan.hibernatecore.dao;

import org.bohdan.hibernatecore.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> getPeopleList() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    public void addPerson(Person person) {
        /*jdbcTemplate.update("INSERT INTO Person(full_name, birth_year) VALUES (?, ?)",
                person.getFullName(),
                person.getBirthYear());*/
    }

    public Person getPersonById(int id) {
        return null;
        /*return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE id=?",
                new BeanPropertyRowMapper<>(Person.class), id);*/
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return null;
        /*return jdbcTemplate.query("SELECT p.full_name FROM Person p WHERE p.full_name=?",
                        new BeanPropertyRowMapper<>(Person.class), fullName)
                .stream()
                .findAny();*/
    }

    public void editPerson(Person person) {
        /*jdbcTemplate.update("UPDATE Person SET full_name=?, birth_year=? WHERE id=?",
                person.getFullName(), person.getBirthYear(), person.getId());*/
    }

    public void deletePerson(int id) {
//        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public Optional<Person> getPersonByBookId(int bookId) {
        return null;
        /*return jdbcTemplate.query(
                        "SELECT p.* FROM Person p" +
                                " INNER JOIN Book b ON p.id = b.person_id" +
                                " WHERE b.id=?",
                        new BeanPropertyRowMapper<>(Person.class), bookId)
                .stream()
                .findAny();*/
    }
}
