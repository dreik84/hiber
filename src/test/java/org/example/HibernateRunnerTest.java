package org.example;

import lombok.Cleanup;
import org.example.entity.*;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.time.Instant;

class HibernateRunnerTest {

    @Test
    void checkH2() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = Company.builder()
                .name("Google")
                .build();

        session.persist(company);

        session.getTransaction().commit();
    }

    @Test
    void checkManyToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        User user = session.find(User.class, 5);
        Chat chat = session.find(Chat.class, 1);
        UserChat userChat = UserChat.builder()
                .chat(chat)
                .user(user)
                .createdAt(Instant.now())
                .createdBy("Andrey")
                .build();

        userChat.setChat(chat);
        userChat.setUser(user);

        session.persist(userChat);

        session.persist(chat);

        session.getTransaction().commit();
    }

    @Test
    void checkOneToOne() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        User user = User.builder()
                .username("john5@mail.ru")
                .build();

        Profile profile = Profile.builder()
                .street("Pobedy 1")
                .build();

        session.persist(user);
        profile.setUser(user);
        session.persist(profile);

        session.getTransaction().commit();
    }

    @Test
    void checkOrphanRemoval() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.find(Company.class, 1);
        company.getUsers().removeIf(user -> user.getId() == 2);

        session.getTransaction().commit();
    }

    @Test
    void addNewUserAndCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = Company.builder()
                .name("Yandex")
                .build();

        User user = User.builder()
                .username("john3@mail.ru")
                .build();

        company.addUser(user);
        session.persist(company);

        session.getTransaction().commit();
    }

    @Test
    void checkOneToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.find(Company.class, 1);
        System.out.println(company.getUsers());

        session.getTransaction().commit();
    }

/*
    @Test
    void testHibernateApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .username("john2@mail.ru")
                .firstname("john")
                .lastname("smitch")
                .birthDate(LocalDate.of(2000, 1, 1))
                .age(23)
                .build();

        String sql = "INSERT INTO %s (%s) VALUES (%s)";

        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(table -> table.schema() + "." + table.name())
                .orElse(user.getClass().getName());

        Field[] fields = user.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(fields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(", "));

        String columnValues = Arrays.stream(fields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hiberdb", "hiberuser", "root");
        PreparedStatement statement = connection
                .prepareStatement(sql.formatted(tableName, columnNames, columnValues));

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            statement.setObject(i + 1, fields[i].get(user));
        }

        statement.executeUpdate();

        statement.close();
        connection.close();
    }
*/
}