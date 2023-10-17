package org.bohdan.hibernatecore.validator;

import org.bohdan.hibernatecore.models.Person;
import org.bohdan.hibernatecore.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class PersonValidator implements Validator, DateValidator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (peopleService.getPersonByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Such name already in the system");
        }

        if (person.getDateOfBirth() != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (!isValid(dateFormat.format(person.getDateOfBirth()))) {
                errors.rejectValue("dateOfBirth", "", "Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }

    @Override
    public boolean isValid(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException ex) {
            return false;
        }
        return true;
    }
}
