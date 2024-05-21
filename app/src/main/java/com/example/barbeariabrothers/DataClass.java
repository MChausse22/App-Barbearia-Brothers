package com.example.barbeariabrothers;

public class DataClass {
    String serviceName;
    String serviceTime;
    String servicePrice;

    public String getUsername() {
        return username;
    }
    String username;

    public DataClass(String serviceName, String serviceTime, String servicePrice, String username) {
        this.serviceName = serviceName;
        this.serviceTime = serviceTime;
        this.servicePrice = servicePrice;
        this.username = username;
    }
    public DataClass(){}

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public String getServicePrice() {
        return servicePrice;
    }
}
