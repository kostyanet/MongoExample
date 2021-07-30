package model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Расширить модель Пользователя:
 * Добавить возраст
 * Добавить место работы
 * Добавить город проживания
 */
@Getter
@Setter
public final class User {
    private String id;
    private String firstName;
    private String lastName;
    private short birthYear;
    private String worksAt;
    private String city;
    private List<String> accounts;

    public User(String firstName, String lastName, short birthYear, String worksAt, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.worksAt = worksAt;
        this.city = city;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthYear=" + birthYear +
                ", worksAt='" + worksAt + '\'' +
                ", city='" + city + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
