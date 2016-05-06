package ua.netcracker.model.entity;

/**
 * User roles (authority granted)
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public enum Role {
    ADMIN(1, "ROLE_ADMIN"), HR(2, "ROLE_HR"), BA(3, "ROLE_BA"), DEV(4, "ROLE_DEV"), STUDENT(5, "ROLE_STUDENT");

    int id;
    String role;

    Role(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return role;
    }


}
