package org.bohdan.hibernatecore;

import org.bohdan.hibernatecore.configuration.HibernateConfiguration;
import org.bohdan.hibernatecore.configuration.RepositoryConfiguration;
import org.bohdan.hibernatecore.model.Director;
import org.bohdan.hibernatecore.repository.DirectorRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                HibernateConfiguration.class,
                RepositoryConfiguration.class
        );

        HibernateConfiguration hibernateConfiguration = applicationContext.getBean("hibernateConfiguration", HibernateConfiguration.class);
        SessionFactory sessionFactory = hibernateConfiguration.sessionFactory();

        DirectorRepository directorRepository = applicationContext.getBean(DirectorRepository.class);

        try (sessionFactory) {
            Director entity = directorRepository.getEntity(Director.class);
            System.out.println(entity);
        }
    }
}