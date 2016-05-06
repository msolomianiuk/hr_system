package ua.netcracker.hr_system.model.dao.daoImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.dao.daoInterface.ReportQueryDAO;
import ua.netcracker.hr_system.model.entity.adminconfiguration.ReportQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private static final String sqlFindAll = "SELECT * FROM \"hr_system\".report;";
    private static final String sqlFindById = "SELECT * FROM \"hr_system\".report WHERE id = (?);";
    private static final String sqlInsert = "INSERT INTO \"hr_system\".report (description, report_query) VALUES (?, ?)";
    private static final String sqlUpdate = "UPDATE \"hr_system\".report SET description=?, report_query=? WHERE id = ?;";
    private static final String sqlDelete = "DELETE FROM \"hr_system\".report WHERE id = ?;";
    private static final String sqlGetDescriptions = "SELECT description FROM \"hr_system\".report;";
    private static final String sqlGetReportQueryByDescription = "SELECT * FROM \"hr_system\".report WHERE decription = (?);";

    @Override
    public List<ReportQuery> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlFindAll);
        List<ReportQuery> reports = new ArrayList<>();
        for (Map row : rows) {
            ReportQuery reportQuery = new ReportQuery();
            reportQuery.setId((Integer) row.get("id"));
            reportQuery.setDescription((String) row.get("description"));
            reportQuery.setQuery((String) row.get("report_query"));
            reports.add(reportQuery);
        }
        return reports;
    }

    @Override
    public ReportQuery find(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        ReportQuery reportQuery = jdbcTemplate.queryForObject(sqlFindById, new Object[]{id}, new RowMapper<ReportQuery>() {
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
        return reportQuery;
    }

    @Override
    public boolean insert(ReportQuery reportQuery) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sqlInsert, new Object[]{reportQuery.getDescription(),
                reportQuery.getQuery()
        });
        return true;

    }

    @Override
    public boolean update(ReportQuery reportQuery) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sqlUpdate, new Object[]{reportQuery.getDescription(),
                reportQuery.getQuery(), reportQuery.getId()
        });
        return true;
    }

    @Override
    public boolean remove(ReportQuery reportQuery) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sqlDelete, new Object[]{reportQuery.getId()});
        return true;
    }

    @Override
    public List<String> getDescriptions() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> descriptions = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlGetDescriptions);
        for (Map row : rows) {
            String description = (String) row.get("description");
            descriptions.add(description);
        }
        return descriptions;
    }

    @Override
    public ReportQuery getReportQueryByDescription(String description) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        ReportQuery reportQuery = jdbcTemplate.queryForObject(sqlGetReportQueryByDescription, new Object[]{description}, new RowMapper<ReportQuery>() {
                    @Override
                    public ReportQuery mapRow(ResultSet resultSet, int i) throws SQLException {
                        return getReportQuery(resultSet);
                    }
                }
        );
        return reportQuery;
    }
}

