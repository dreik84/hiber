package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.*;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {

        Company company = Company.builder()
                .name("Google")
                .build();

        User user = User.builder()
                .username("john1@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("john")
                        .lastname("smitch")
                        .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
                        .build())
                .role(Role.USER)
                .company(company)
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session1 = sessionFactory.openSession()) {

            session1.beginTransaction();

            session1.persist(company);
            session1.persist(user);

            session1.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
        }
    }
}
