package org.example;

import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
//        configuration.addAnnotatedClass(User.class);
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.persist(User.builder()
                    .username("john@mail.ru")
                    .firstname("john")
                    .lastname("smitch")
                    .birthDate(LocalDate.of(2000, 01, 01))
                    .age(23)
                    .build());

            session.getTransaction().commit();
        }
    }
}
