package ua.netcracker.model.service;

import ua.netcracker.model.entity.Address;

/**
 * Created by MaXim on 06.05.2016.
 */
public interface AddressService extends EntityService<Address> {
    void insert(Address address);

    Address findByAddress(String address);
}
