package org.bohdan.hibernatecore.dao;

import org.bohdan.hibernatecore.models.Book;
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

    @Transactional
    public void addPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();

        session.persist(person);
    }

    @Transactional(readOnly = true)
    public Person getPersonById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Person.class, id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getPersonByFullName(String fullName) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT p FROM Person p WHERE p.fullName=:fullName", Person.class)
                .setParameter("fullName", fullName)
                .uniqueResultOptional();
    }

    @Transactional
    public void editPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();

        Person personToUpdate = session.get(Person.class, person.getId());

        personToUpdate.setFullName(person.getFullName());
        personToUpdate.setBirthYear(person.getBirthYear());
    }

    @Transactional
    public void deletePerson(int id) {
        Session session = sessionFactory.getCurrentSession();

        session.remove(session.get(Person.class, id));
    }

    @Transactional(readOnly = true)
    public Optional<Person> getPersonByBookId(int bookId) {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, bookId);

        return Optional.ofNullable(book.getPerson());
    }
}
