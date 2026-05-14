package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.UserRepository;
import org.example.mapper.CompanyReadMapper;
import org.example.mapper.UserReadMapper;
import org.example.service.UserService;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session = (Session) Proxy.newProxyInstance(
                    SessionFactory.class.getClassLoader(),
                    new Class[]{Session.class},
                    ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1))
            );

            session.beginTransaction();

            var companyReadMapper = new CompanyReadMapper();
            var userReadMapper = new UserReadMapper(companyReadMapper);

            var userRepository = new UserRepository(session);
            var userService = new UserService(userRepository, userReadMapper);

            userService.findUserById(1L).ifPresent(System.out::println);

            session.getTransaction().commit();
        }
    }
}
