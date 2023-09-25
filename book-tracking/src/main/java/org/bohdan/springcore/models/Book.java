package org.bohdan.springcore.models;

import jakarta.validation.constraints.*;

public class Book {
    private Integer id;
    private Integer personId;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = 2, max = 100, message = "Book name must be between 2 and 100 characters")
    private String name;

    @NotEmpty(message = "Author can't be empty")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    @Pattern(regexp = "[A-ZА-ЯІЇ]\\w+ [A-ZА-ЯІЇ]\\w+", message = "Provide author name in this format: Name Surname")
    private String author;

    @NotNull(message = "Provide book year")
    @Min(value = 1, message = "Year can't be zero")
    @Max(value = 2023, message = "Year can't be greater than 2023")
    private Integer year;

    public Book(Integer id, Integer personId, String name, String author, Integer year) {
        this.id = id;
        this.personId = personId;
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

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
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
}
