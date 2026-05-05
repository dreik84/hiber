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

            user = session.find(User.class, 1L);
            User user1 = session.find(User.class, 1L);

            session.getTransaction().commit();
        }

        try (Session session1 = sessionFactory.openSession()) {

            session1.beginTransaction();

            user = session1.find(User.class, 1L);

            session1.getTransaction().commit();
        }
    }
}
