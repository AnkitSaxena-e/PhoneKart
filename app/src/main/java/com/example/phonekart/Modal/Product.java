package com.example.phonekart.Modal;

public class Product {

    private String Pid, Date, Time, Price, TName, TDetail, TBrand, Keyward, Color, Quantity, SearchP, SellerId, SnR, Image1, ProductName;

    public Product() {
    }

    public Product(String pid, String date, String time, String price, String TName, String TDetail, String TBrand, String keyward, String color,
                   String quantity, String searchP, String sellerId, String snR, String image1, String productName) {
        Pid = pid;
        Date = date;
        Time = time;
        Price = price;
        this.TName = TName;
        this.TDetail = TDetail;
        this.TBrand = TBrand;
        Keyward = keyward;
        Color = color;
        Quantity = quantity;
        SearchP = searchP;
        SellerId = sellerId;
        SnR = snR;
        Image1 = image1;
        ProductName = productName;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTName() {
        return TName;
    }

    public void setTName(String TName) {
        this.TName = TName;
    }

    public String getTDetail() {
        return TDetail;
    }

    public void setTDetail(String TDetail) {
        this.TDetail = TDetail;
    }

    public String getTBrand() {
        return TBrand;
    }

    public void setTBrand(String TBrand) {
        this.TBrand = TBrand;
    }

    public String getKeyward() {
        return Keyward;
    }

    public void setKeyward(String keyward) {
        Keyward = keyward;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getSearchP() {
        return SearchP;
    }

    public void setSearchP(String searchP) {
        SearchP = searchP;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getSnR() {
        return SnR;
    }

    public void setSnR(String snR) {
        SnR = snR;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
}