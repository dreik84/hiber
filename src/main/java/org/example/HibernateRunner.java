package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.CompanyRepository;
import org.example.dao.UserRepository;
import org.example.dto.UserCreateDto;
import org.example.entity.Birthday;
import org.example.entity.PersonalInfo;
import org.example.entity.Role;
import org.example.mapper.CompanyReadMapper;
import org.example.mapper.UserCreateMapper;
import org.example.mapper.UserReadMapper;
import org.example.service.UserService;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;
import java.time.LocalDate;

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
            var companyRepository = new CompanyRepository(session);
            var userCreateMapper = new UserCreateMapper(companyRepository);
            var userService = new UserService(userRepository, userReadMapper, userCreateMapper);

            userService.findUserById(1L).ifPresent(System.out::println);

            UserCreateDto userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Anna")
                            .lastname("Petrova")
                            .birthDate(new Birthday(LocalDate.now()))
                            .build(),
                    "anna@mail.ru",
                    Role.USER,
                    1
            );

            System.out.println(userService.create(userCreateDto));

            session.getTransaction().commit();
        }
    }
}
