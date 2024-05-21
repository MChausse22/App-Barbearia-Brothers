package com.example.barbeariabrothers;

public class AgendamentoClass {
    String day, month, year, id;
    String username, barber, service;

    public AgendamentoClass(String id){
        this.id = id;
    }
    public AgendamentoClass(String day, String month, String year, String username, String barber, String service) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.username = username;
        this.barber = barber;
        this.service = service;
    }

    public AgendamentoClass(String day, String month, String barber, String service) {
        this.day = day;
        this.month = month;
        this.barber = barber;
        this.service = service;
    }

    public AgendamentoClass(String day, String month, String year, String username, String service){
        this.day = day;
        this.month = month;
        this.year = year;
        this.username = username;
        this.service = service;
    }


    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getBarber() {
        return barber;
    }
    public void setBarber(String barber) {
        this.barber = barber;
    }

    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
