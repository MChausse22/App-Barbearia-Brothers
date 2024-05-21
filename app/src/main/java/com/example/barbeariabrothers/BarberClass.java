package com.example.barbeariabrothers;

public class BarberClass {
    String name, password, username, service, userBarber;

    public BarberClass(String name, String username, String service) {
        this.name = name;
        this.username = username;
        this.service = service;
    }
    public BarberClass(String name, String username, String service, String userBarber) {
        this.name = name;
        this.username = username;
        this.service = service;
        this.userBarber = userBarber;
    }
    public BarberClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    public String getService() {
        return service;
    }

    public String getUserBarber() {
        return userBarber;
    }
}
