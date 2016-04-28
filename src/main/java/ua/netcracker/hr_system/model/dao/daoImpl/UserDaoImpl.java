package ua.netcracker.hr_system.model.dao.daoImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.connection.ConnectionManager;
import ua.netcracker.hr_system.model.dao.daoInterface.UserDao;
import ua.netcracker.hr_system.model.entity.Candidate;
import ua.netcracker.hr_system.model.entity.Role;
import ua.netcracker.hr_system.model.entity.User;
import ua.netcracker.hr_system.model.utils.RolesUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Repository("userDao")
public class UserDaoImpl implements UserDao {
    static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
    private User user;
  //  @Autowired
    //private CandidateDaoImpl candidateDao;
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Override
    public User findByName(String name) {
        return null;
    }

    @Override
    public User findBySurname(String surname) {
        return null;
    }

    @Override
    public List<User> getAllPersonalById(int roleId) {

        List list = null;

        if (roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4) {
            jdbcTemplate = new JdbcTemplate(dataSource);

            String sql = "SELECT u.id , u.name , u.email, u.surname, u.patronymic " +
                    "FROM \"hr_system\".users u " +
                    "JOIN \"hr_system\".role_users_maps rol ON rol.user_id = u.id " +
                    "WHERE rol.role_id=" + roleId;

            List<User> listOfUser = jdbcTemplate.query(sql, new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
                    User user = new User();

                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));

                    return user;
                }
            });
            list = listOfUser;
        }
        return list;
    }

    @Override
    public User findByEmail(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            //load information about user
            stmt = conn.prepareStatement("select * from \"hr_system\".users where lower(email)=lower(?)");
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        null);
                LOGGER.debug("load user in database: " + user.toString());

                rs.close();
                stmt.close();
                //load user roles
                stmt = conn.prepareStatement("select name from \"hr_system\".role " +
                        "JOIN \"hr_system\".role_users_maps ON \"hr_system\".role_users_maps.role_id=\"hr_system\".role.id " +
                        "where user_id=?");
                stmt.setInt(1, user.getId());

                rs = stmt.executeQuery();

                List<Role> auths = new ArrayList<>();
                while (rs.next()) {
                    String roleStr = rs.getString("name");
                    Role role = RolesUtils.getRoleByStr(roleStr);
                    if (role == null) {
                        throw new Exception("Incorrect role " + roleStr);
                    }
                    auths.add(role);
                }
                user.setRoles(auths);
                LOGGER.debug("load roles with user: " + user.toString());
                return user;
            }

        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                LOGGER.error(e);
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOGGER.error(e);
            }
            ConnectionManager.releaseConnection(conn);
        }
        return null;
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public boolean insert(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();

            //insert user
            stmt = conn.prepareStatement("insert into \"hr_system\".users (email,password,name," +
                    "surname,patronymic) values (lower(?),?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getSurname());
            stmt.setString(5, user.getPatronymic());


            if (stmt.executeUpdate() == 1) {
                rs = stmt.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    user.setId((int) rs.getLong(1));

                    rs.close();
                    stmt.close();

                    //insert users roles
                    stmt = conn.prepareStatement("insert into \"hr_system\".role_users_maps (role_id,user_id) " +
                            "values (?,?)");

                    for (Role role : user.getRoles()) {
                        stmt.setInt(1, role.getId());
                        stmt.setInt(2, user.getId());
                        stmt.addBatch();
                    }
                    int[] result = stmt.executeBatch();
                    if (result.length == user.getRoles().size()) {
                        conn.commit();
                        //candidateDao.insert(new Candidate(user.getId(), 1, 1));
                        return true;
                    }

                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(e);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                LOGGER.error(e);
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOGGER.error(e);
            }
            ConnectionManager.releaseConnection(conn);
        }
        return false;
    }

    @Override
    public boolean update(User elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(User elem) {
        throw new UnsupportedOperationException();
    }
}
