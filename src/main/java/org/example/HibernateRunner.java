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
             Session session = sessionFactory.openSession();
             Session session1 = sessionFactory.openSession()) {

            session.beginTransaction();
            session1.beginTransaction();

            User user = session.find(User.class, 2L);

            Payment payment = session.find(Payment.class, 1L, LockModeType.OPTIMISTIC);
            payment.setAmount(payment.getAmount() + 10);

            Payment payment1 = session1.find(Payment.class, 1L, LockModeType.OPTIMISTIC);
            payment1.setAmount(payment1.getAmount() + 30);

//            Payment payment = Payment.builder()
//                    .amount(600)
//                    .receiver(user)
//                    .build();
//
//            session.persist(payment);


            session.getTransaction().commit();
            session1.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
        }
    }
}
