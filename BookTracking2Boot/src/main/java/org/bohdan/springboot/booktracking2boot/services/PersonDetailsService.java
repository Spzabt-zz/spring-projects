package org.bohdan.springboot.booktracking2boot.services;

import org.bohdan.springboot.booktracking2boot.models.Person;
import org.bohdan.springboot.booktracking2boot.repositories.PersonRepository;
import org.bohdan.springboot.booktracking2boot.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> byFullName = personRepository.findByFullName(username);

        return new PersonDetails(byFullName.orElseThrow(() -> new UsernameNotFoundException("User not found!")));
    }
}
