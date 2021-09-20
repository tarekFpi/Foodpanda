package com.example.myfoodpanda.Model;

public class userAll_order_model {

    private String serial_id;
    private String invoie_id;
    private String areaName;
    private String categoryName;
    private String RestName;
    private String sub_menu;
    private String product_Name;
    private int Sell_price;
    private int quantity;
    private int Discount;
    private String Delivary_time;
    private String Delivary_free;
    private String user_gmail;
    private String userName;
    private String userArea;
    private String flat_on;
    private String city;
    private String phone;
    private int total_price;

    public userAll_order_model(String serial_id, String invoie_id, String areaName, String categoryName, String restName, String sub_menu, String product_Name, int sell_price, int quantity, int discount, String delivary_time, String delivary_free, String user_gmail, String userName, String userArea, String flat_on, String city, String phone, int total_price) {
        this.serial_id = serial_id;
        this.invoie_id = invoie_id;
        this.areaName = areaName;
        this.categoryName = categoryName;
        RestName = restName;
        this.sub_menu = sub_menu;
        this.product_Name = product_Name;
        Sell_price = sell_price;
        this.quantity = quantity;
        Discount = discount;
        Delivary_time = delivary_time;
        Delivary_free = delivary_free;
        this.user_gmail = user_gmail;
        this.userName = userName;
        this.userArea = userArea;
        this.flat_on = flat_on;
        this.city = city;
        this.phone = phone;
        this.total_price = total_price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
    }

    public String getInvoie_id() {
        return invoie_id;
    }

    public void setInvoie_id(String invoie_id) {
        this.invoie_id = invoie_id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRestName() {
        return RestName;
    }

    public void setRestName(String restName) {
        RestName = restName;
    }

    public String getSub_menu() {
        return sub_menu;
    }

    public void setSub_menu(String sub_menu) {
        this.sub_menu = sub_menu;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public int getSell_price() {
        return Sell_price;
    }

    public void setSell_price(int sell_price) {
        Sell_price = sell_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public String getDelivary_time() {
        return Delivary_time;
    }

    public void setDelivary_time(String delivary_time) {
        Delivary_time = delivary_time;
    }

    public String getDelivary_free() {
        return Delivary_free;
    }

    public void setDelivary_free(String delivary_free) {
        Delivary_free = delivary_free;
    }

    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }


    public String getFlat_on() {
        return flat_on;
    }

    public void setFlat_on(String flat_on) {
        this.flat_on = flat_on;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

