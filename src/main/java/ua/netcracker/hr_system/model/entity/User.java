package ua.netcracker.hr_system.model.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Entity <b>User</b> (table <b>users</b>)
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
@Component
public class User implements Serializable {
    /**
     * User id
     */
    private int id;
    /**
     * User email
     */
    private String email;
    /**
     * User password
     */
    private String password;
    /**
     * User name
     */
    private String name;
    /**
     * User surname
     */
    private String surname;
    /**
     * User patronymic
     */
    private String patronymic;
    /**
     * User authority granted
     */
    private List<Role> roles;
    /**
     * Path for user photo
     */
    private String image;

    public User() {
    }

    public User(String email, String password, String name, String surname, String patronymic, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.roles = roles;
    }

    public User(int id, String email, String password, String name, String surname, String patronymic, List<Role> roles, String image) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.roles = roles;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", roles=" + roles +
                '}';
    }
}
