package ua.netcracker.hr_system.model.dao.connection;

import org.apache.log4j.Logger;
import ua.netcracker.hr_system.model.dao.daoImpl.UserDAOImpl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public class ConnectionManager {

    static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    /**
     * Database data source
     */
    private static final DataSource dataSource;

    static {
        try {
            dataSource = (DataSource) new InitialContext().lookup("jdbc/HRSystemPool");
        } catch (NamingException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Get connection to the DB
     *
     * @return database connection
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

    /**
     * Close DB connection
     *
     * @param conn database connection
     */
    public static void releaseConnection(Connection conn) {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
    }
}
