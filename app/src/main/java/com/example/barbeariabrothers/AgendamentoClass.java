package com.example.barbeariabrothers;

public class AgendamentoClass {
    String day, month, year, id;
    String clientID, barberID, serviceID;
    String hour, status;
    int statusID;

    public AgendamentoClass(String day, String month, String year, String id, String clientID, String barberID, String serviceID, String hour, String status, int statusID) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.id = id;
        this.clientID = clientID;
        this.barberID = barberID;
        this.serviceID = serviceID;
        this.hour = hour;
        this.status = status;
        this.statusID = statusID;
    }

    public AgendamentoClass() {
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getClientID() {
        return clientID;
    }

    public String getBarberID() {
        return barberID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getId() {
        return id;
    }

    public String getHour() {
        return hour;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setBarberID(String barberID) {
        this.barberID = barberID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
}
