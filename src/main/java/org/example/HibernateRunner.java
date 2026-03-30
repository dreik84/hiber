package org.example;

import org.example.converter.BirthdayConverter;
import org.example.entity.Birthday;
import org.example.entity.Role;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAttributeConverter(new BirthdayConverter(), true);
//        configuration.addAnnotatedClass(User.class);
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = User.builder()
                    .username("john1@mail.ru")
                    .firstname("john")
                    .lastname("smitch")
                    .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
                    .role(Role.USER)
                    .build();

//            session.persist(user);
//            User user1 = session.get(User.class, "john1@mail.ru");
            User user1 = session.find(User.class, "john1@mail.ru");
            user1.setFirstname("john");
//            session.flush();
//            session.clear();
            session.evict(user1);
            System.out.println(user1);

            session.getTransaction().commit();
        }
    }
}
