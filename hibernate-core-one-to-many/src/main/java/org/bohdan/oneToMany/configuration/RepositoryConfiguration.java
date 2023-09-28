package org.bohdan.oneToMany.configuration;

import org.bohdan.oneToMany.repository.CommonDaoRepoImpl;
import org.bohdan.oneToMany.repository.DirectorRepository;
import org.bohdan.oneToMany.repository.MovieRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {
    private final HibernateConfiguration sessionFactoryConfiguration;

    public RepositoryConfiguration(HibernateConfiguration sessionFactoryConfiguration) {
        this.sessionFactoryConfiguration = sessionFactoryConfiguration;
    }

    @Bean
    public DirectorRepository directorRepository() {
        return new DirectorRepository(sessionFactoryConfiguration.sessionFactory());
    }

    @Bean
    public MovieRepository movieRepository() {
        return new MovieRepository(sessionFactoryConfiguration.sessionFactory());
    }

    @Bean
    public CommonDaoRepoImpl commonDaoRepo() {
        return new CommonDaoRepoImpl(sessionFactoryConfiguration.sessionFactory());
    }
}
