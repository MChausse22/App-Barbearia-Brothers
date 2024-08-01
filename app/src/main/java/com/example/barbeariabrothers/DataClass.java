package com.example.barbeariabrothers;

public class DataClass {
    String serviceID;
    String serviceName;
    String serviceTime;
    String servicePrice;
    public DataClass(String serviceId, String serviceName, String serviceTime, String servicePrice) {
        this.serviceID = serviceId;
        this.serviceName = serviceName;
        this.serviceTime = serviceTime;
        this.servicePrice = servicePrice;
    }
    public DataClass(){

    }

    public String getServiceID() {
        return serviceID;
    }
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
