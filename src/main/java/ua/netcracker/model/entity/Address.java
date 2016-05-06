package ua.netcracker.model.entity;

import org.springframework.stereotype.Component;

/**
 * Created by MaXim on 03.05.2016.
 */

@Component
public class Address {
    private int id;
    private String address;
    private int roomCapacity;

    public Address() {
    }

    public Address(int id, String address, int roomCapacity) {
        this.id = id;
        this.address = address;
        this.roomCapacity = roomCapacity;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", roomCapacity=" + roomCapacity +
                '}';
    }
}

