package com.googleapis.roomandme;

public class User {
    private String Name;
    private String Email;
    private String Contact;
    private String Lat;
    private String Lng;

    public User(){

    }

    public User(String name, String email, String contact, String lat, String lng){
        Name = name;
        Email = email;
        Contact = contact;
        Lat= lat;
        Lng= lng;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat= lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng= lng;
    }
}
