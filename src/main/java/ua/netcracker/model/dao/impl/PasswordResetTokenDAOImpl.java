package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.PasswordResetTokenDAO;
import ua.netcracker.model.entity.PasswordResetToken;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created on 13:18 11.05.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
@Repository("passwordResetTokenDAO")
public class PasswordResetTokenDAOImpl implements PasswordResetTokenDAO {
    private static final Logger LOGGER = Logger.getLogger(PasswordResetTokenDAOImpl.class);

    private static final String SQL_FIND = "SELECT * FROM \"hr_system\".reset_token WHERE id=?";
    private static final String SQL_FIND_ALL = "SELECT * FROM \"hr_system\".reset_token";
    private static final String SQL_UPDATE = "UPDATE \"hr_system\".reset_token SET user_id=?,token=?" +
            " WHERE id=?";
    private static final String SQL_REMOVE = "DELETE FROM \"hr_system\".reset_token WHERE id=?";
    private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM \"hr_system\".reset_token res " +
            "JOIN \"hr_system\".users us ON us.id = res.user_id " +
            "WHERE us.id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setSimpleJdbcInsert(DataSource dataSource) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".reset_token").
                usingColumns("user_id", "token").
                usingGeneratedKeyColumns("id");
    }

    private PasswordResetToken createTokenWithResultSet(ResultSet rs) throws SQLException {
        PasswordResetToken prt = new PasswordResetToken();
        prt.setId(rs.getInt("id"));
        prt.setUserId(rs.getInt("user_id"));
        prt.setToken(rs.getString("token"));
        return prt;
    }

    @Override
    public Collection<PasswordResetToken> findAll() {
        List<PasswordResetToken> tokens = null;
        try {
            tokens = jdbcTemplate.query(SQL_FIND_ALL, new RowMapper<PasswordResetToken>() {
                @Override
                public PasswordResetToken mapRow(ResultSet rs, int rowNumber) throws SQLException {
                    return createTokenWithResultSet(rs);
                }
            });
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return tokens;
    }

    @Override
    public PasswordResetToken find(int id) {
        PasswordResetToken token = null;
        try {
            token = jdbcTemplate.queryForObject(
                    SQL_FIND,
                    new RowMapper<PasswordResetToken>() {
                        public PasswordResetToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return createTokenWithResultSet(rs);
                        }
                    }, id);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return token;
    }

    @Override
    public boolean insert(PasswordResetToken entity) {
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("user_id", entity.getUserId());
        insertParameter.addValue("token", entity.getToken());
        try {
            Number key = simpleJdbcInsert.executeAndReturnKey(insertParameter);
            if (key != null) {
                entity.setId(key.intValue());
                return true;
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public boolean update(PasswordResetToken entity) {
        try {
            return jdbcTemplate.update(SQL_UPDATE, entity.getUserId(), entity.getToken(), entity.getId()) == 1;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public boolean remove(int id) {
        try {
            return jdbcTemplate.update(SQL_REMOVE, id) == 1;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public PasswordResetToken findByUserId(int userId) {
        PasswordResetToken token = null;
        try {
            token = jdbcTemplate.queryForObject(
                    SQL_FIND_BY_USER_ID,
                    new RowMapper<PasswordResetToken>() {
                        public PasswordResetToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return createTokenWithResultSet(rs);
                        }
                    }, userId);

        } catch (Exception e) {
            LOGGER.error(e);
        }
        return token;
    }
}
