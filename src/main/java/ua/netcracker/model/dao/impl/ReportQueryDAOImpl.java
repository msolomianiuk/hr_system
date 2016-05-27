package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.ReportQueryDAO;
import ua.netcracker.model.entity.ReportQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Владимир on 28.04.2016.
 */
@Repository("reportQueryDAO")
public class ReportQueryDAOImpl implements ReportQueryDAO {

    static final Logger LOGGER = Logger.getLogger(ReportQueryDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    private static final String SQL_FIND_ALL = "SELECT * FROM \"hr_system\".report;";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM \"hr_system\".report WHERE id = (?);";
    private static final String SQL_INSERT = "INSERT INTO \"hr_system\".report (description, report_query, \"isimportant\") VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE \"hr_system\".report SET description=?, report_query=?, \"isimportant\"=? WHERE id = (?);";
    private static final String SQL_DELETE = "DELETE FROM \"hr_system\".report WHERE id = (?);";
    private static final String SQL_GET_DESCRIPTIONS = "SELECT description FROM \"hr_system\".report;";
    private static final String SQL_GET_REPORT_QUERY_BY_DESCRIPTION = "SELECT * FROM \"hr_system\".report WHERE decription = (?);";
    private static final String SQL_FIND_BY_IMPORTANCE = "SELECT * FROM \"hr_system\".report WHERE \"isimportant\" = (?);";
    private static final String SQL_UPDATE_IMPORTANCE = "UPDATE \"hr_system\".report\n SET \"isimportant\" = ? WHERE id = (?);";

    @Override
    public Collection<ReportQuery> findAll() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ALL);
            Collection<ReportQuery> reports = new ArrayList<>();
            for (Map row : rows) {
                ReportQuery reportQuery = new ReportQuery();
                reportQuery.setId((int) row.get("id"));
                reportQuery.setDescription((String) row.get("description"));
                reportQuery.setQuery((String) row.get("report_query"));
                reportQuery.setShow((boolean) row.get("isimportant"));
                reports.add(reportQuery);
            }
            return reports;
        } catch (DataAccessException ex) {
            LOGGER.trace(ex);
            return new ArrayList<>();
        }
    }

    @Override
    public ReportQuery find(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            ReportQuery reportQuery = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, new RowMapper<ReportQuery>() {
                        @Override
                        public ReportQuery mapRow(ResultSet resultSet, int i) throws SQLException {
                            return getReportQuery(resultSet);
                        }
                    }
            );
            return reportQuery;
        } catch (DataAccessException ex) {
            LOGGER.trace(ex);
            return new ReportQuery();
        }
    }

    private ReportQuery getReportQuery(ResultSet resultSet) throws SQLException {
        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setId(resultSet.getInt("id"));
        reportQuery.setDescription(resultSet.getString("description"));
        reportQuery.setQuery(resultSet.getString("report_query"));
        reportQuery.setShow(resultSet.getBoolean("isimportant"));
        return reportQuery;
    }

    @Override
    public boolean insert(ReportQuery reportQuery) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(SQL_INSERT, new Object[]{reportQuery.getDescription(),
                    reportQuery.getQuery(), reportQuery.isShow()
            });
            return true;
        } catch (DataAccessException ex) {
            LOGGER.trace(ex);
            return false;
        }

    }

    @Override
    public boolean update(ReportQuery reportQuery) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(SQL_UPDATE, new Object[]{reportQuery.getDescription(),
                    reportQuery.getQuery(), reportQuery.isShow(), reportQuery.getId()
            });
            return true;
        } catch (DataAccessException ex) {
            LOGGER.trace(ex);
            return false;
        }
    }

    @Override
    public boolean delete(ReportQuery reportQuery) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(SQL_DELETE, new Object[]{reportQuery.getId()});
            return true;
        } catch (DataAccessException ex) {
            LOGGER.trace(ex);
            return false;
        }
    }

    @Override
    public Collection<ReportQuery> findAllByImportant(boolean isImportant) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_BY_IMPORTANCE, new Object[]{isImportant});
            Collection<ReportQuery> reports = new ArrayList<>();
            for (Map row : rows) {
                ReportQuery reportQuery = new ReportQuery();
                reportQuery.setId((int) row.get("id"));
                reportQuery.setDescription((String) row.get("description"));
                reportQuery.setQuery((String) row.get("report_query"));
                reportQuery.setShow((boolean) row.get("isimportant"));
                reports.add(reportQuery);
            }
            return reports;
        } catch (DataAccessException ex) {
            LOGGER.trace(ex);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateImportance(ReportQuery reportQuery, boolean isImportant) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(SQL_UPDATE_IMPORTANCE, new Object[]{reportQuery.getId(), isImportant
            });
            return true;
        } catch (DataAccessException ex) {
            LOGGER.trace(ex);
            return false;
        }
    }
}

