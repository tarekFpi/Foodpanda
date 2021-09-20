package com.example.myfoodpanda.Model;

public class Category_model {
    private String serial_id;
    private String areaName;
    private String categoryName;
    private String RestName;
    private String sub_menu;
    private String product_Name;
    private int Sell_price;
    private String Delivary_time;
    private String Deatils;
    private String Image;
    private boolean Expandable;

    public boolean isExpandable() {
        return Expandable;
    }

    public void setExpandable(boolean expandable) {
        Expandable = expandable;
    }


    public Category_model(String serial_id, String areaName, String categoryName, String restName, String sub_menu, String product_Name, int sell_price, String delivary_time, String deatils, String image) {
        this.serial_id = serial_id;
        this.areaName = areaName;
        this.categoryName = categoryName;
        RestName = restName;
        this.sub_menu = sub_menu;
        this.product_Name = product_Name;
        Sell_price = sell_price;
        Delivary_time = delivary_time;
        Deatils = deatils;
        Image = image;
    }

    public String getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
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

    public String getDelivary_time() {
        return Delivary_time;
    }

    public void setDelivary_time(String delivary_time) {
        Delivary_time = delivary_time;
    }

    public String getDeatils() {
        return Deatils;
    }

    public void setDeatils(String deatils) {
        Deatils = deatils;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
