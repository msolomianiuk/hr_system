package ua.netcracker.model.entity;

/**
 * User roles (authority granted)
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public enum Role {
    ROLE_ADMIN(1), ROLE_HR(2), ROLE_BA(3), ROLE_DEV(4), ROLE_STUDENT(5);

    int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name();
    }


}
