package ua.netcracker.hr_system.model.dao.daoImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.netcracker.hr_system.model.entity.Address;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by MaXim on 05.05.2016.
 */
@Repository("adressDao")
public class AddressDAOImpl {
    private static final Logger LOGGER = Logger.getLogger(AddressDAOImpl.class);
    @Autowired
    private DataSource dataSource;


    public boolean insert(Address address) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("\"hr_system\".address").
                usingColumns("address", "room_capacity");
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("address",address.getAddress());
        insertParameter.addValue("room_capacity",address.getRoomCapacity());
        return simpleJdbcInsert.execute(insertParameter) == 5007 ? true : false;
    }

    public boolean remove(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "delete from \"hr_system\".address where id=?";
        jdbcTemplate.update(sql, id);
        return true;
    }

    public List<Address> getAll(){
        List<Address> addressList = null;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT id, address, room_capacity" +
                "  FROM \"hr_system\".address";
        addressList = jdbcTemplate.query(sql,
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


    public boolean update(Address address) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "UPDATE \"hr_system\".address" +
                "   SET address=?, room_capacity=?" +
                " WHERE id=?";
        return jdbcTemplate.update(sql,address.getAddress(),address.getRoomCapacity(),address.getId())>0;
    }

}
