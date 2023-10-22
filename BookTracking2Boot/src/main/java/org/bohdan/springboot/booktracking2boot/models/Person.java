package org.bohdan.springboot.booktracking2boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Please provide date of birth")
    @Past(message = "Date should be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
