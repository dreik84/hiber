package org.example;

import jakarta.persistence.LockModeType;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Payment;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {



        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = session.find(User.class, 2L);
            Payment payment = session.find(Payment.class, 1L, LockModeType.OPTIMISTIC);

//            Payment payment = Payment.builder()
//                    .amount(600)
//                    .receiver(user)
//                    .build();
//
//            session.persist(payment);

            System.out.println(payment);

            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
        }
    }
}
