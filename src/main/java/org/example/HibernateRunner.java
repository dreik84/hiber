package org.example;

import org.example.entity.Birthday;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) {

        // TRANSIENT
        User user = User.builder()
                .username("john1@mail.ru")
                .firstname("john")
                .lastname("smitch")
                .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
                .role(Role.USER)
                .build();

        log.info("User object in transient state: {}, {}", user, user);

        // TRANSIENT
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session1 = sessionFactory.openSession()) {

            session1.beginTransaction();

            session1.merge(user);
            user.setFirstname("Anna");
            log.warn("User first name was changed: {}", user);
            System.out.println(session1.isDirty());
            System.out.println(user);
            log.debug("User: {}, session: {}", user, session1);

            session1.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
        }
    }
}
