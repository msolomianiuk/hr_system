package ua.netcracker.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.AddressDAO;
import ua.netcracker.model.entity.Address;
import ua.netcracker.model.service.AddressService;

import java.util.Collection;
import java.util.List;

/**
 * Created by MaXim on 06.05.2016.
 */
@Service("address service")
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDAO addressDAO;

    @Override
    public Address findById(int id) {
        return addressDAO.find(id);
    }

    @Override
    public Address findByAddress(String address) {
        return addressDAO.findByAdrress(address);
    }

    @Override
    public List<Address> findAllSetting() {
        return (List<Address>) addressDAO.findAll();
    }

    @Override
    public void saveOrUpdate(Address address) {
        addressDAO.update(address);
    }

    @Override
    public void delete(long id) {
        addressDAO.remove(id);
    }

    @Override
    public void insert(Address address){
        addressDAO.insert(address);
    }

}
