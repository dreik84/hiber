package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {

        User user = null;
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            user = User.builder()
                    .username("tom")
                    .build();

            session.persist(user);

            session.getTransaction().commit();
        }
    }
}
