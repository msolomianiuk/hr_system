package ua.netcracker.model.service;

import ua.netcracker.model.entity.Address;

/**
 * Created by MaXim on 06.05.2016.
 */
public interface AddressService extends EntityService<Address> {
    public void insert(Address address);
}
