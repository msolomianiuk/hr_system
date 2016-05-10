package ua.netcracker.model.dao;

import ua.netcracker.model.entity.Address;

/**
 * Created by MaXim on 06.05.2016.
 */
public interface AddressDAO extends DAO<Address> {
    boolean remove(long id);

    Address findByAdrress(String Address);
}
