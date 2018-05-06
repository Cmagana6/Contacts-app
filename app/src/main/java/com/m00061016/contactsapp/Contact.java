package com.m00061016.contactsapp;

public class Contact {

    private String Name,Phone;
    private int Photo;

    public Contact() {
    }

    //Definiendo el constructor de la clase

    public Contact(String name, String phone, int photo) {
        Name = name;
        Phone = phone;
        Photo = photo;
    }

    //Getter


    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public int getPhoto() {
        return Photo;
    }

    //Setter


    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

}
