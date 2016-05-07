package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * @author Bersik (Serhii Kisilchuk)
 */
@Repository("userDao")
public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    private static final String SQL_FIND = "SELECT * FROM \"hr_system\".users WHERE id=?";
    private static final String SQL_FIND_ALL = "SELECT * FROM \"hr_system\".users";
    private static final String SQL_UPDATE = "UPDATE \"hr_system\".users SET email=?,password=?,name=?,surname=?," +
            "patronymic=?,image=? WHERE id=?";
    private static final String SQL_REMOVE = "DELETE FROM \"hr_system\".users WHERE id=?";
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM \"hr_system\".users WHERE name=?";
    private static final String SQL_FIND_BY_SURNAME = "SELECT * FROM \"hr_system\".users WHERE surname=?";
    private static final String SQL_GET_ALL_PERSONAL_BY_ROLE = "SELECT * " +
            "FROM \"hr_system\".users u " +
            "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
            "WHERE rol.role_id=?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM \"hr_system\".users WHERE " +
            "lower(email)=lower(?)";
    private static final String SQL_GET_USER_ROLES_BY_USER_ID = "SELECT name FROM \"hr_system\".role " +
            "JOIN \"hr_system\".role_users_maps ON \"hr_system\".role_users_maps.role_id=\"hr_system\".role.id " +
            " WHERE user_id=?";
    private static final String SQL_INSERT_USER_ROLES = "INSERT INTO \"hr_system\".role_users_maps (role_id,user_id) " +
            " VALUES (?,?)";
    private static final String SQL_REMOVE_USER_ROLES = "DELETE FROM \"hr_system\".role_users_maps WHERE user_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setSimpleJdbcInsert(DataSource dataSource) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".users").
                usingColumns("email", "password", "name", "surname", "patronymic").
                usingGeneratedKeyColumns("id");
    }

    @Override
    public Collection<User> findAll() {
        List<User> users = jdbcTemplate.query(SQL_FIND_ALL, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return createUserWithResultSet(rs);
            }
        });
        for (User user : users) {
            user.setRoles(getUserRolesById(user.getId()));
        }
        return users;
    }

    @Override
    public User find(int id) {
        User user = jdbcTemplate.queryForObject(
                SQL_FIND,
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return createUserWithResultSet(rs);
                    }
                }, id);

        user.setRoles(getUserRolesById(user.getId()));
        return user;
    }

    @Override
    public boolean insert(User user) {
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("email", user.getEmail());
        insertParameter.addValue("password", user.getPassword());
        insertParameter.addValue("name", user.getName());
        insertParameter.addValue("surname", user.getSurname());
        insertParameter.addValue("patronymic", user.getPatronymic());
        insertParameter.addValue("image", user.getImage());
        try {
            Number key = simpleJdbcInsert.executeAndReturnKey(insertParameter);
            if (key != null) {
                user.setId(key.intValue());
                return insertUserRoles(user);
            }
        } catch (DuplicateKeyException ex) {
            return false;
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        if (jdbcTemplate.update(SQL_UPDATE, user.getEmail(), user.getPassword(), user.getName(), user.getSurname(),
                user.getPatronymic(), user.getImage(), user.getId()) == 1) {
            if (removeUserRoles(user.getId()))
                return insertUserRoles(user);
        }
        return false;
    }

    @Override
    public boolean remove(User user) {
        if (jdbcTemplate.update(SQL_REMOVE, user.getId()) == 1) {
            return removeUserRoles(user.getId());
        }
        return false;
    }

    @Override
    public List<User> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_BY_NAME, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return createUserWithResultSet(rs);
            }
        }, name);
    }

    @Override
    public List<User> findBySurname(String surname) {
        return jdbcTemplate.query(SQL_FIND_BY_SURNAME, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return createUserWithResultSet(rs);
            }
        }, surname);
    }

    @Override
    public List<User> getAllPersonalById(int roleId) {
        List<User> users = null;
        if (roleId >= 1 && roleId <= 4) {
            users = jdbcTemplate.query(SQL_GET_ALL_PERSONAL_BY_ROLE, new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
                    return createUserWithResultSet(rs);
                }
            }, roleId);
        }
        return users;
    }

    @Override
    public User findByEmail(String email) {
        User user = jdbcTemplate.queryForObject(
                SQL_FIND_BY_EMAIL,
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return createUserWithResultSet(rs);
                    }
                }, email);
        user.setRoles(getUserRolesById(user.getId()));
        return user;
    }

    private User createUserWithResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setPatronymic(rs.getString("patronymic"));
        user.setImage(rs.getString("image"));
        return user;
    }


    @Override
    public List<Role> getUserRolesById(int userId) {
        return jdbcTemplate.query(
                SQL_GET_USER_ROLES_BY_USER_ID,
                new RowMapper<Role>() {
                    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return Role.valueOf(rs.getString("name"));
                    }
                }, userId);
    }

    @Override
    public boolean insertUserRoles(final User user) {
        final Object[] roles = user.getRoles().toArray();
        int[] res = jdbcTemplate.batchUpdate(SQL_INSERT_USER_ROLES, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Role role = (Role) (roles[i]);
                preparedStatement.setInt(1, role.getId());
                preparedStatement.setInt(2, user.getId());
            }

            @Override
            public int getBatchSize() {
                return roles.length;
            }
        });
        for (int i : res) {
            if (i != 1)
                return false;
        }
        return true;
    }

    @Override
    public boolean removeUserRoles(int userId) {
        return jdbcTemplate.update(SQL_REMOVE_USER_ROLES, userId) > 0;
    }
}