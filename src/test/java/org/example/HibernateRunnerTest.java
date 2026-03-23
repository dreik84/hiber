package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.example.entity.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

class HibernateRunnerTest {

    @Test
    void testHibernateApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .username("john1@mail.ru")
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

}