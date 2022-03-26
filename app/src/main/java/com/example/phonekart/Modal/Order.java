package com.example.phonekart.Modal;

public class Order {

    private String TotalAmount, Name, PhoneNumber, Address, ProductN, City, Pin, ProductPID, FromUser, ToUser, FromUserN, ToUserN, Check,
    KeyWord, Return, ONO, PID, Date, Time, DeliveryDetail, Quantity;

    public Order() {
    }

    public Order(String totalAmount, String name, String phoneNumber, String address, String productN, String city, String pin, String productPID,
                 String fromUser, String toUser, String fromUserN, String toUserN, String check, String keyWord, String aReturn, String ONO, String PID, String date,
                 String time, String deliveryDetail, String quantity) {
        TotalAmount = totalAmount;
        Name = name;
        PhoneNumber = phoneNumber;
        Address = address;
        ProductN = productN;
        City = city;
        Pin = pin;
        ProductPID = productPID;
        FromUser = fromUser;
        ToUser = toUser;
        FromUserN = fromUserN;
        ToUserN = toUserN;
        Check = check;
        KeyWord = keyWord;
        Return = aReturn;
        this.ONO = ONO;
        this.PID = PID;
        Date = date;
        Time = time;
        DeliveryDetail = deliveryDetail;
        Quantity = quantity;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }

    public String getProductPID() {
        return ProductPID;
    }

    public void setProductPID(String productPID) {
        ProductPID = productPID;
    }

    public String getFromUser() {
        return FromUser;
    }

    public void setFromUser(String fromUser) {
        FromUser = fromUser;
    }

    public String getToUser() {
        return ToUser;
    }

    public void setToUser(String toUser) {
        ToUser = toUser;
    }

    public String getCheck() {
        return Check;
    }

    public void setCheck(String check) {
        Check = check;
    }

    public String getKeyWord() {
        return KeyWord;
    }

    public void setKeyWord(String keyWord) {
        KeyWord = keyWord;
    }

    public String getReturn() {
        return Return;
    }

    public void setReturn(String aReturn) {
        Return = aReturn;
    }

    public String getONO() {
        return ONO;
    }

    public void setONO(String ONO) {
        this.ONO = ONO;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDeliveryDetail() {
        return DeliveryDetail;
    }

    public void setDeliveryDetail(String deliveryDetail) {
        DeliveryDetail = deliveryDetail;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getFromUserN() {
        return FromUserN;
    }

    public void setFromUserN(String fromUserN) {
        FromUserN = fromUserN;
    }

    public String getToUserN() {
        return ToUserN;
    }

    public void setToUserN(String toUserN) {
        ToUserN = toUserN;
    }

    public String getProductN() {
        return ProductN;
    }

    public void setProductN(String productN) {
        ProductN = productN;
    }
}
