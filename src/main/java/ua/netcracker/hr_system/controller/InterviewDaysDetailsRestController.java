package ua.netcracker.hr_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.hr_system.model.dao.daoImpl.AddressDAOImpl;
import ua.netcracker.hr_system.model.dao.daoImpl.InterviewDaysDetailsDAOImpl;
import ua.netcracker.hr_system.model.entity.Address;
import ua.netcracker.hr_system.model.entity.InterviewDaysDetails;
import ua.netcracker.hr_system.model.service.serviceImpl.InterviewDaysDetailsServiceImpl;
import ua.netcracker.hr_system.model.service.serviceInterface.InterviewDaysDetailsService;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaXim on 28.04.2016.
 */
@RestController
public class InterviewDaysDetailsRestController {

    //    @Autowired(required = false)
    private InterviewDaysDetails interviewDaysDetails;
    private Address addressEnt;
    private AddressDAOImpl addressDAO;

    @Autowired
    private void setInterviewDaysDetails(InterviewDaysDetails interviewDaysDetails) {
        this.interviewDaysDetails = interviewDaysDetails;
    }

    @Autowired
    private void setAddressEnt(Address addressEnt) {
        this.addressEnt = addressEnt;
    }

    @Autowired
    private InterviewDaysDetailsServiceImpl interviewDaysDetailsDao;

    @Autowired
    private void setInterviewDaysDetailsService(InterviewDaysDetailsServiceImpl interviewDaysDetailsService) {
        this.interviewDaysDetailsDao = interviewDaysDetailsService;
    }

    @RequestMapping(value = "/inter_day_insert", method = RequestMethod.GET)
    public ResponseEntity<InterviewDaysDetails> setInterviewDaysDetails(
            @RequestParam String date,
            @RequestParam String start_time,
            @RequestParam String end_time,
            @RequestParam String address_id
    ) {
        interviewDaysDetails.setCourseId(1);
        interviewDaysDetails.setInterviewDate(date);
        interviewDaysDetails.setStartTime(start_time);
        interviewDaysDetails.setEndTime(end_time);
        interviewDaysDetails.setAddressId(Integer.parseInt(address_id));
        interviewDaysDetailsDao.insert(interviewDaysDetails);
        if (interviewDaysDetails == null) {
            return ResponseEntity.accepted().body(interviewDaysDetails);
        }
        return ResponseEntity.ok(interviewDaysDetails);
    }

    @RequestMapping(value = "/inter_day_list", method = RequestMethod.GET)
    public ResponseEntity<List<InterviewDaysDetails>> getAll() {
        List<InterviewDaysDetails> interview = interviewDaysDetailsDao.findAllSetting();
        if (interview.isEmpty()) {
            return new ResponseEntity<List<InterviewDaysDetails>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<InterviewDaysDetails>>(interview, HttpStatus.OK);
    }


    @RequestMapping(value = "/inter_day_deleteInt", method = RequestMethod.GET)
    public ResponseEntity<Address> removeInterviewController(
            @RequestParam String id
    ) {
        interviewDaysDetailsDao.delete(Integer.parseInt(id));
        if (addressEnt == null) {
            return ResponseEntity.accepted().body(addressEnt);
        }
        return ResponseEntity.ok(addressEnt);
    }

    @RequestMapping(value = "/inter_day_updateInt", method = RequestMethod.GET)
    public ResponseEntity<InterviewDaysDetails> updateInterviewController(
            @RequestParam String id,
            @RequestParam String date,
            @RequestParam String start_time,
            @RequestParam String end_time,
            @RequestParam String address_id

    ) {
        InterviewDaysDetails interviewDaysDetails = new InterviewDaysDetails(
                Integer.parseInt(id),
                date,
                start_time,
                Integer.parseInt(address_id),
                end_time
        );
        interviewDaysDetailsDao.saveOrUpdate(interviewDaysDetails);
        if (interviewDaysDetails == null) {
            return ResponseEntity.accepted().body(interviewDaysDetails);
        }
        return ResponseEntity.ok(interviewDaysDetails);
    }

    @RequestMapping(value = "/inter_day_insert_address", method = RequestMethod.GET)
    public ResponseEntity<Address> setAddressMethod(
            @RequestParam String address,
            @RequestParam String roomCapacity
    ) {
        addressEnt.setAddress(address);
        addressEnt.setRoomCapacity(Integer.parseInt(roomCapacity));
        addressDAO.insert(addressEnt);
        if (addressEnt == null) {
            return ResponseEntity.accepted().body(addressEnt);
        }
        return ResponseEntity.ok(addressEnt);
    }

    @RequestMapping(value = "/inter_day_delete_address", method = RequestMethod.GET)
    public ResponseEntity<Address> removeAddress(
            @RequestParam String id
    ) {
        addressDAO.remove(Integer.parseInt(id));
        if (addressEnt == null) {
            return ResponseEntity.accepted().body(addressEnt);
        }
        return ResponseEntity.ok(addressEnt);
    }

    @RequestMapping(value = "/inter_day_address_list", method = RequestMethod.GET)
    public ResponseEntity<List<Address>> getAllAddressController() {
        List<Address> addressList = addressDAO.getAll();
        if (addressList.isEmpty()) {
            return new ResponseEntity<List<Address>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
    }

    @RequestMapping(value = "/inter_day_update_address", method = RequestMethod.GET)
    public ResponseEntity<Address> updateAddressController(
            @RequestParam String id,
            @RequestParam String address,
            @RequestParam String roomCapacity
    ) {
        Address addressEntiti = new Address(
                Integer.parseInt(id),
                address,
                Integer.parseInt(roomCapacity)
        );
        addressDAO.update(addressEntiti);
        if (addressEntiti == null) {
            return ResponseEntity.accepted().body(addressEntiti);
        }
        return ResponseEntity.ok(addressEntiti);
    }

}
