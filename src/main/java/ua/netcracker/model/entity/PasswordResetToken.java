package ua.netcracker.model.entity;

/**
 * Created on 13:07 11.05.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
public class PasswordResetToken {
    private int id;

    private int userId;

    private String token;

    public PasswordResetToken() {
    }

    public PasswordResetToken(int id, int userId, String token) {
        this.id = id;
        this.userId = userId;
        this.token = token;
    }

    public PasswordResetToken(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordResetToken that = (PasswordResetToken) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        return token != null ? token.equals(that.token) : that.token == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}
