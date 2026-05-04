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

//            session.createNativeQuery("SET TRANSACTION READ ONLY ").executeUpdate();

            User user = session.find(User.class, 2L);

            Payment payment = session.find(Payment.class, 1L, LockModeType.OPTIMISTIC);
            payment.setAmount(payment.getAmount() + 10);

//            Payment payment = Payment.builder()
//                    .amount(600)
//                    .receiver(user)
//                    .build();
//
//            session.persist(payment);

            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
        }
    }
}
