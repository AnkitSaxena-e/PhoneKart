package com.example.phonekart.Modal;

public class Users {

    private String Name, Number, Passward, ShopName, UserId, Image, Address, Pin, UID;

    public Users() {
    }

    public Users(String name, String number, String passward, String shopName, String userId, String image, String address, String pin, String UID) {
        Name = name;
        Number = number;
        Passward = passward;
        ShopName = shopName;
        UserId = userId;
        Image = image;
        Address = address;
        Pin = pin;
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPassward() {
        return Passward;
    }

    public void setPassward(String passward) {
        Passward = passward;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
