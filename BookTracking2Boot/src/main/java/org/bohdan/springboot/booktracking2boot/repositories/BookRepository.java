package org.bohdan.springboot.booktracking2boot.repositories;

import org.bohdan.springboot.booktracking2boot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameStartingWith(String name);
}
