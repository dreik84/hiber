package org.example;

import org.example.entity.Birthday;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {

        // TRANSIENT
        User user = User.builder()
                .username("john1@mail.ru")
                .firstname("john")
                .lastname("smitch")
                .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
                .role(Role.USER)
                .build();

        // TRANSIENT
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session1 = sessionFactory.openSession()) {

            session1.beginTransaction();

            // PERSISTENT к session1 и TRANSIENT к session2
            session1.persist(user);

            session1.getTransaction().commit();
        }

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session2 = sessionFactory.openSession()) {

            session2.beginTransaction();

            // Сначала GET потом DELETE PERSISTENCE к session2 DETACHED к session1
            session2.remove(user);

            // REMOVED к session2
            session2.getTransaction().commit();
        }
    }
}
