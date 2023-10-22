package org.bohdan.springboot.booktracking2boot.services;

import jakarta.persistence.EntityNotFoundException;
import org.bohdan.springboot.booktracking2boot.models.Book;
import org.bohdan.springboot.booktracking2boot.models.Person;
import org.bohdan.springboot.booktracking2boot.repositories.BookRepository;
import org.bohdan.springboot.booktracking2boot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PeopleService(PersonRepository personRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> getPeopleList() {
        return personRepository.findAll();
    }

    @Transactional
    public void addPerson(Person person) {
        person.setCreatedAt(new Date());

        personRepository.save(person);
    }

    public Person getPersonById(int id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return personRepository.findByFullName(fullName);
    }

    @Transactional
    public void editPerson(Person person) {
        Person personFromDb = personRepository.findById(person.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Person with id: %d not found", person.getId())));

        person.setCreatedAt(personFromDb.getCreatedAt());

        personRepository.save(person);
    }

    @Transactional
    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> getPersonByBookId(int bookId) {
        Book bookById = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book by id: %d not found", bookId)));

        return Optional.ofNullable(bookById.getPerson());
    }
}
