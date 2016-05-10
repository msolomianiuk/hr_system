package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String SQL_INSERT = "INSERT INTO \"hr_system\".report (description, report_query, \"isImportant\") VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE \"hr_system\".report SET description=?, report_query=?, \"isImportant\"=? WHERE id = (?);";
    private static final String SQL_DELETE = "DELETE FROM \"hr_system\".report WHERE id = (?);";
    private static final String SQL_GET_DESCRIPTIONS = "SELECT description FROM \"hr_system\".report;";
    private static final String SQL_GET_REPORT_QUERY_BY_DESCRIPTION = "SELECT * FROM \"hr_system\".report WHERE decription = (?);";
    private static final String SQL_FIND_BY_IMPORTANCE = "SELECT * FROM \"hr_system\".report WHERE \"isImportant\" = (?);";
    private static final String SQL_UPDATE_IMPORTANCE = "UPDATE \"hr_system\".report\n SET \"isImportant\" = ? WHERE id = (?);";

    @Override
    public Collection<ReportQuery> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ALL);
        List<ReportQuery> reports = new ArrayList<>();
        for (Map row : rows) {
            ReportQuery reportQuery = new ReportQuery();
            reportQuery.setId((int) row.get("id"));
            reportQuery.setDescription((String) row.get("description"));
            reportQuery.setQuery((String) row.get("report_query"));
            reportQuery.setShow((boolean) row.get("isImportant"));
            reports.add(reportQuery);
        }
        return reports;
    }

    @Override
    public ReportQuery find(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        ReportQuery reportQuery = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, new RowMapper<ReportQuery>() {
                    @Override
                    public ReportQuery mapRow(ResultSet resultSet, int i) throws SQLException {
                        return getReportQuery(resultSet);
                    }
                }
        );
        return reportQuery;
    }

    private ReportQuery getReportQuery(ResultSet resultSet) throws SQLException {
        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setId(resultSet.getInt("id"));
        reportQuery.setDescription(resultSet.getString("description"));
        reportQuery.setQuery(resultSet.getString("report_query"));
        reportQuery.setShow(resultSet.getBoolean("isImportant"));
        return reportQuery;
    }

    @Override
    public boolean insert(ReportQuery reportQuery) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(SQL_INSERT, new Object[]{reportQuery.getDescription(),
                reportQuery.getQuery(), reportQuery.isShow()
        });
        return true;

    }

    @Override
    public boolean update(ReportQuery reportQuery) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(SQL_UPDATE, new Object[]{reportQuery.getDescription(),
                reportQuery.getQuery(), reportQuery.isShow(), reportQuery.getId()
        });
        return true;
    }

    @Override
    public boolean remove(ReportQuery reportQuery) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(SQL_DELETE, new Object[]{reportQuery.getId()});
        return true;
    }

    @Override
    public List<String> getDescriptions() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> descriptions = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_DESCRIPTIONS);
        for (Map row : rows) {
            String description = (String) row.get("description");
            descriptions.add(description);
        }
        return descriptions;
    }

    @Override
    public ReportQuery getReportQueryByDescription(String description) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        ReportQuery reportQuery = jdbcTemplate.queryForObject(SQL_GET_REPORT_QUERY_BY_DESCRIPTION, new Object[]{description}, new RowMapper<ReportQuery>() {
                    @Override
                    public ReportQuery mapRow(ResultSet resultSet, int i) throws SQLException {
                        return getReportQuery(resultSet);
                    }
                }
        );
        return reportQuery;
    }

    @Override
    public Collection<ReportQuery> findAllByImportant(boolean isImportant) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_BY_IMPORTANCE, new Object[]{isImportant});
        List<ReportQuery> reports = new ArrayList<>();
        for (Map row : rows) {
            ReportQuery reportQuery = new ReportQuery();
            reportQuery.setId((int) row.get("id"));
            reportQuery.setDescription((String) row.get("description"));
            reportQuery.setQuery((String) row.get("report_query"));
            reportQuery.setShow((boolean) row.get("isImportant"));
            reports.add(reportQuery);
        }
        return reports;
    }

    @Override
    public boolean updateImportance(ReportQuery reportQuery, boolean isImportant) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(SQL_UPDATE_IMPORTANCE, new Object[]{reportQuery.getId(), isImportant
        });
        return true;
    }
}

