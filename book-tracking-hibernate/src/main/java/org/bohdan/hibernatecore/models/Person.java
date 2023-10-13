package org.bohdan.hibernatecore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Name can't be empty. Please, enter your full name")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @Pattern(
            regexp = "[A-ZА-ЯІЇ][a-zа-яії]+ [A-ZА-ЯІЇ][a-zа-яії]+ [A-ZА-ЯІЇ][a-zа-яії]+",
            message = "No X Æ A-12 is allowed, please, provide full name in this format: John Johnson Doe"
    )
    @Column(name = "full_name")
    private String fullName;

    @NotNull(message = "Provide birth year")
    @Min(value = 1, message = "Your birth year can't be zero")
    @Max(value = 2023, message = "Your birth year can't be greater than 2023")
    @Column(name = "birth_year")
    private Integer birthYear;

    @OneToMany(mappedBy = "person")
    private List<Book> books;

    public Person(String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public Person() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
}
