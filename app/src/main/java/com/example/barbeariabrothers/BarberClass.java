package com.example.barbeariabrothers;

public class BarberClass {
    String id, name, serviceID;

    public BarberClass(String name, String id, String serviceID) {
        this.name = name;
        this.id = id;
        this.serviceID = serviceID;
    }

    public BarberClass() {

    }

    public String getServiceID() {
        return serviceID;
    }
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
