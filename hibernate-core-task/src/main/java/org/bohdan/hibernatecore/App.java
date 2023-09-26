package org.bohdan.hibernatecore;

import org.bohdan.hibernatecore.configuration.HibernateConfiguration;
import org.bohdan.hibernatecore.configuration.RepositoryConfiguration;
import org.bohdan.hibernatecore.model.Director;
import org.bohdan.hibernatecore.model.Movie;
import org.bohdan.hibernatecore.repository.DirectorRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                HibernateConfiguration.class,
                RepositoryConfiguration.class
        );

        HibernateConfiguration hibernateConfiguration = applicationContext
                .getBean("hibernateConfiguration", HibernateConfiguration.class);

        SessionFactory sessionFactory = hibernateConfiguration.sessionFactory();

        DirectorRepository directorRepository = applicationContext
                .getBean("directorRepository", DirectorRepository.class);

        try (sessionFactory) {
            Director entity = directorRepository.getEntityById(Director.class, 2);
            System.out.println(entity);

            List<Movie> listOfEntitiesByEntityId = directorRepository.getListOfEntitiesByEntityId(Director.class, entity.getId());
            System.out.println(listOfEntitiesByEntityId);
        }
    }
}
