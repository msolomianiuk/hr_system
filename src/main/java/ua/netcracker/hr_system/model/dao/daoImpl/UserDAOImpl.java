package ua.netcracker.hr_system.model.dao.daoImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.daoInterface.UserDAO;
import ua.netcracker.hr_system.model.entity.Role;
import ua.netcracker.hr_system.model.entity.User;
import ua.netcracker.hr_system.model.utils.RolesUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


@Repository("userDao")
public class UserDAOImpl implements UserDAO {

    static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);
    //private User user;

    @Autowired(required = false)
    private User user;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Override
    public List<User> findByName(String name) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "SELECT * FROM \"hr_system\".users WHERE name='" + name + "'";

        List<User> users = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return createUserWithResultSet(rs);
            }
        });
        return users;
    }

    @Override
    public List<User> findBySurname(String surname) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "SELECT * FROM \"hr_system\".users WHERE surname='" + surname + "'";

        List<User> users = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return createUserWithResultSet(rs);
            }
        });
        return users;
    }

    @Override
    public List<User> getAllPersonalById(int roleId) {

        List<User> users = null;

        if (roleId >= 1 && roleId <= 4) {
            jdbcTemplate = new JdbcTemplate(dataSource);

            String sql = "SELECT * " +
                    "FROM \"hr_system\".users u " +
                    "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                    "WHERE rol.role_id=" + roleId;

            users = jdbcTemplate.query(sql, new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
                    return createUserWithResultSet(rs);
                }
            });
        }
        return users;
    }

    @Override
    public User findByEmail(String email) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from \"hr_system\".users where lower(email)=lower('" + email + "')";

        User user = jdbcTemplate.queryForObject(
                sql,
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return createUserWithResultSet(rs);
                    }
                });
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
    public Collection<User> findAll() {

        List<User> users = null;

        jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "SELECT * " +
                "FROM \"hr_system\".users u " +
                "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                "WHERE rol.role_id=";

        users = jdbcTemplate.query(sql, new RowMapper<User>() {

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
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from \"hr_system\".users where id=" + id + ")";

        User user = jdbcTemplate.queryForObject(
                sql,
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return createUserWithResultSet(rs);
                    }
                });

        user.setRoles(getUserRolesById(user.getId()));
        return user;
    }

    @Override
    public List<Role> getUserRolesById(int userId) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select name from \"hr_system\".role " +
                "JOIN \"hr_system\".role_users_maps ON \"hr_system\".role_users_maps.role_id=\"hr_system\".role.id " +
                "where user_id=" + userId;
        List<Role> roles = jdbcTemplate.query(
                sql,
                new RowMapper<Role>() {
                    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return RolesUtils.getRoleByStr(rs.getString("name"));
                    }
                });
        return roles;
    }

    @Override
    public boolean insert(User user) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".users").
                usingColumns("email", "password", "name", "surname", "patronymic").
                usingGeneratedKeyColumns("id");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("email", user.getEmail());
        insertParameter.addValue("password", user.getPassword());
        insertParameter.addValue("name", user.getName());
        insertParameter.addValue("surname", user.getSurname());
        insertParameter.addValue("patronymic", user.getPatronymic());
        insertParameter.addValue("image", user.getImage());
        Number key = simpleJdbcInsert.executeAndReturnKey(insertParameter);
        if (key != null) {
            user.setId(key.intValue());
            return insertUserRoles(user);
        }
        return false;
    }

    @Override
    public boolean insertUserRoles(final User user) {
        String sql = "insert into \"hr_system\".role_users_maps (role_id,user_id) values (?,?)";
        jdbcTemplate = new JdbcTemplate(dataSource);
        final Object[] roles = user.getRoles().toArray();
        int[] res = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
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
    public boolean update(User user) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "update \"hr_system\".users set email=?,password=?,name=?,surname=?,patronymic=?,image=? " +
                "where id=?";

        if (jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getName(), user.getSurname(),
                user.getPatronymic(), user.getImage(), user.getId()) == 1) {
            if (removeUserRoles(user.getId()))
                return insertUserRoles(user);
        }
        return false;
    }

    @Override
    public boolean remove(User user) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "delete from \"hr_system\".users where id=?";

        if (jdbcTemplate.update(sql, user.getId()) == 1) {
            return removeUserRoles(user.getId());
        }
        return false;
    }

    @Override
    public boolean removeUserRoles(int userId) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "delete from \"hr_system\".role_users_maps where user_id=?";
        return jdbcTemplate.update(sql, userId) > 0;

    }
}
