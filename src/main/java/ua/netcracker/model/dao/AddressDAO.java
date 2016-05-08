package ua.netcracker.model.dao;

import ua.netcracker.model.entity.Address;

/**
 * Created by MaXim on 06.05.2016.
 */
public interface AddressDAO extends DAO<Address> {
    public boolean remove(long id);
    public boolean remove(Address address);
}
