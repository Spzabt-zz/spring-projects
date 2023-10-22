package org.bohdan.hibernatecore.repositories;

import org.bohdan.hibernatecore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameStartingWith(String name);
}
