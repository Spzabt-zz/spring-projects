package org.bohdan.springboot.booktracking2boot.services;

import org.bohdan.springboot.booktracking2boot.models.Person;
import org.bohdan.springboot.booktracking2boot.models.enums.Role;
import org.bohdan.springboot.booktracking2boot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person, Role role) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));

        person.setCreatedAt(new Date());

        person.setRole(role);

        personRepository.save(person);
    }
}
