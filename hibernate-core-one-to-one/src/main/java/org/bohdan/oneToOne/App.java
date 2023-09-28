package org.bohdan.oneToOne;

import org.bohdan.oneToOne.model.Principal;
import org.bohdan.oneToOne.model.School;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Principal.class)
                .addAnnotatedClass(School.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (sessionFactory) {
            getPrincipalsSchool(sessionFactory);

            getSchoolsPrincipal(sessionFactory);

            connectDirectorAndSchool(sessionFactory);

            changeDirectorInExistingSchool(sessionFactory);

            tryToAddSecondSchoolForExistingPrincipal(sessionFactory); // throws violation of unique constraint "school.principal_id"
        }
    }

    private static void tryToAddSecondSchoolForExistingPrincipal(SessionFactory sessionFactory) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        School school = new School(15);
        Principal principal = session.get(Principal.class, 8);

        principal.setSchool(school);

        session.persist(school);

        session.getTransaction().commit();
    }

    private static void changeDirectorInExistingSchool(SessionFactory sessionFactory) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Principal newPrincipal = new Principal("Topchii", 100);
        School school = session.get(School.class, 7);

        newPrincipal.setSchool(school);

        session.persist(newPrincipal);

        session.getTransaction().commit();
    }

    private static void connectDirectorAndSchool(SessionFactory sessionFactory) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Principal principal = new Principal("NewVasil", 64);
        School school = new School(9);

        principal.setSchool(school);

        session.persist(principal);

        session.getTransaction().commit();
    }

    private static void getSchoolsPrincipal(SessionFactory sessionFactory) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        School school = session.get(School.class, 2);
        Principal principal = school.getPrincipal();
        System.out.println(principal + "\n");

        session.getTransaction().commit();
    }

    private static void getPrincipalsSchool(SessionFactory sessionFactory) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Principal principal = session.get(Principal.class, 2);
        School school = principal.getSchool();
        System.out.println(school + "\n");

        session.getTransaction().commit();
    }
}
