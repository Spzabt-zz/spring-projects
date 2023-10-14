package org.bohdan.hibernatecore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = 2, max = 100, message = "Book name must be between 2 and 100 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Author can't be empty")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    @Pattern(regexp = "[A-ZА-ЯІЇ]\\w+ [A-ZА-ЯІЇ]\\w+", message = "Provide author name in this format: Name Surname")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Provide book year")
    @Min(value = 1, message = "Year can't be zero")
    @Max(value = 2023, message = "Year can't be greater than 2023")
    @Column(name = "year")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    public Book(String name, String author, Integer year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
