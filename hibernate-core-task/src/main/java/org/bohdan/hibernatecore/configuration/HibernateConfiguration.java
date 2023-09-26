package org.bohdan.hibernatecore.configuration;

import org.bohdan.hibernatecore.model.Director;
import org.bohdan.hibernatecore.model.Movie;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {
    @Bean
    public org.hibernate.cfg.Configuration configuration() {
        return new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Director.class)
                .addAnnotatedClass(Movie.class);
    }

    @Bean
    public SessionFactory sessionFactory() {
        return configuration().buildSessionFactory();
    }
}
