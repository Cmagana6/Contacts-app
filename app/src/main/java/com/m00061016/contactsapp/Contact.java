package com.m00061016.contactsapp;

public class Contact {

    private int id;
    private String Name,Phone;
    private int Photo;
    private boolean isFav = false;

    public Contact() {
    }

    //Definiendo el constructor de la clase

    public Contact(String name, String phone, int photo,int id) {
        Name = name;
        Phone = phone;
        Photo = photo;
        isFav = false;
        this.id= id;
    }

    //Getter

    public int getId(){ return this.id;}

    public boolean getFavstatus(){ return isFav;}

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

    public void setFavStatus(boolean status){ isFav = status;}

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

    public void toggle(){this.isFav = !this.isFav;}

    public void setId(int id){this.id = id;}

}
