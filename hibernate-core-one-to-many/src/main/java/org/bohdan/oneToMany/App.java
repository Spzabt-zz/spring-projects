package org.bohdan.oneToMany;

import org.bohdan.oneToMany.configuration.HibernateConfiguration;
import org.bohdan.oneToMany.configuration.RepositoryConfiguration;
import org.bohdan.oneToMany.model.Director;
import org.bohdan.oneToMany.model.Movie;
import org.bohdan.oneToMany.repository.DirectorRepository;
import org.bohdan.oneToMany.repository.MovieRepository;
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
        MovieRepository movieRepository = applicationContext
                .getBean("movieRepository", MovieRepository.class);

        try (sessionFactory) {
            // 1. Get director
            Director director = directorRepository.getEntityById(Director.class, 2);
            System.out.println(director + "\n");

            // 2. Get list of director films
            List<Movie> listOfEntitiesByEntityId = movieRepository.getListOfEntitiesByEntityId(Director.class, director.getId());
            System.out.println(listOfEntitiesByEntityId + "\n");

            // 3. Get movie
            Movie movie = movieRepository.getEntityById(Movie.class, 2);
            System.out.println(movie + "\n");

            // 4. Get director of the movie
            Director director1 = directorRepository.getOwnerByHisEntity(Movie.class, movie.getId());
            System.out.println(director1 + "\n");

            // 5. Add movie for random director
            movieRepository.addEntityForTheOwner(Director.class, director.getId());
            listOfEntitiesByEntityId = movieRepository.getListOfEntitiesByEntityId(Director.class, director.getId());
            System.out.println(listOfEntitiesByEntityId + "\n");

            // 6. Add new director and film for him
            directorRepository.addNewDirectorAndNewMovie();

            // 7. Change director in existing film
            directorRepository.changeDirectorInExistingFilm(Movie.class, 14, director.getId());

            // 8. Remove directors film
            movieRepository.removeDirectorsFilm(Director.class, director.getId(), 15);
        }
    }
}
