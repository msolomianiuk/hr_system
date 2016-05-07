package ua.netcracker.model.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Created by MaXim on 07.05.2016.
 */
@Service
public class JdbcTemplateFactory {

    @Autowired
    DataSource dataSource;

    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
