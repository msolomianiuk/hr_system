package ua.netcracker.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.model.dao.AddressDAO;
import ua.netcracker.model.entity.Address;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by MaXim on 05.05.2016.
 */
@Repository("adressDao")
public class AddressDAOImpl implements AddressDAO {
    private static final Logger LOGGER = Logger.getLogger(AddressDAOImpl.class);

    private static final String UPDATE_SQL = "UPDATE \"hr_system\".address SET address=?, room_capacity=? WHERE id=?";
    private static final String REMOVE_SQL = "delete from \"hr_system\".address where id=?";
    private static final String FIND_ALL_SQL = "SELECT id, address, room_capacity FROM \"hr_system\".address ORDER BY id";
    private static final String FIND_SQL = "";

    @Autowired
    private DataSource dataSource;

    @Override
    public Address find(int id) {
        return null;
    }

    @Override
    public boolean insert(Address address) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".address").
                usingColumns("address", "room_capacity");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("address",address.getAddress());
        insertParameter.addValue("room_capacity",address.getRoomCapacity());
        return simpleJdbcInsert.execute(insertParameter) == 5007 ? true : false;
    }

    @Override
    public boolean remove(long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(REMOVE_SQL, id);
        return true;
    }

    @Override
    public boolean remove(Address address) {
        return false;
    }

    @Override
    public List<Address> findAll(){
        List<Address> addressList = null;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        addressList = jdbcTemplate.query(FIND_ALL_SQL,
                new RowMapper<Address>() {
                    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return createAddressWithResultSet(rs);
                    }
                });
        return addressList;
    }

    private Address createAddressWithResultSet(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("id"));
        address.setAddress(rs.getString("address"));
        address.setRoomCapacity(rs.getInt("room_capacity"));
        return address;
    }

    @Override
    public boolean update(Address address) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update(UPDATE_SQL,address.getAddress(),address.getRoomCapacity(),address.getId())>0;
    }

}
