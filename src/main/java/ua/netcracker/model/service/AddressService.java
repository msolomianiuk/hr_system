package ua.netcracker.model.service;

import ua.netcracker.model.entity.Address;

import java.util.Collection;

/**
 * Created by MaXim on 06.05.2016.
 */
public interface AddressService {
    void insert(Address address);

    Address findById(int id);

    Collection<Address> findAllSetting();

    void saveOrUpdate(Address address);

    boolean delete(long id);

    Address findByAddress(String address);
}
