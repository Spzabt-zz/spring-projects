package org.bohdan.hibernatecore;

import org.bohdan.hibernatecore.model.Book;
import org.bohdan.hibernatecore.model.Writer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Writer.class)
                .addAnnotatedClass(Book.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();

        try (sessionFactory) {
            Transaction transaction = currentSession.beginTransaction();

            insertWritersAndBooks(currentSession);

            getBookWriters(currentSession);

            getWriterBooks(currentSession);

            transaction.commit();
        }
    }

    private static void getWriterBooks(Session currentSession) {
        Writer writer = currentSession.get(Writer.class, 1);
        System.out.println(writer.getBooks());
    }

    private static void getBookWriters(Session currentSession) {
        Book book = currentSession.get(Book.class, 1);
        System.out.println(book.getWriters());
    }

    private static void insertWritersAndBooks(Session currentSession) {
        Book book = new Book("Brand new book", 1982);
        Writer writer1 = new Writer("Writer1", 89);
        Writer writer2 = new Writer("Writer2", 77);

        book.setWriters(new ArrayList<>(List.of(writer1, writer2)));

        writer1.setBooks(new ArrayList<>(Collections.singletonList(book)));
        writer2.setBooks(new ArrayList<>(Collections.singletonList(book)));

        currentSession.persist(book);

        currentSession.persist(writer1);
        currentSession.persist(writer2);
    }
}
