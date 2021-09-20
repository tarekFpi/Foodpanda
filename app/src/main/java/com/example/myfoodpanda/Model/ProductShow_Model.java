package com.example.myfoodpanda.Model;

public class ProductShow_Model {
    private String serial_id;
    private String areaName;
    private String categoryName;
    private String Restaurants_name;
    private String sub_menu;
    private String Delivary_time;
    private String Rest_Deatils;
    private  int  delivary_free;
    private String closeing_time;
    private String Rest_Image;
    private boolean IsDisables;

    public ProductShow_Model(String serial_id, String areaName, String categoryName, String restaurants_name, String sub_menu, String delivary_time, String rest_Deatils, int delivary_free, String closeing_time, String rest_Image) {
        this.serial_id = serial_id;
        this.areaName = areaName;
        this.categoryName = categoryName;
        this.Restaurants_name = restaurants_name;
        this.sub_menu = sub_menu;
        this.Delivary_time = delivary_time;
        this.Rest_Deatils = rest_Deatils;
        this.delivary_free = delivary_free;
        this.closeing_time = closeing_time;
        this.Rest_Image = rest_Image;
    }

    public boolean isDisables() {
        return IsDisables;
    }

    public void setDisables(boolean disables) {
        IsDisables = disables;
    }

    public String getCloseing_time() {
        return closeing_time;
    }

    public void setCloseing_time(String closeing_time) {
        this.closeing_time = closeing_time;
    }

    public String getRest_Deatils() {
        return Rest_Deatils;
    }

    public void setRest_Deatils(String rest_Deatils) {
        Rest_Deatils = rest_Deatils;
    }

    public int getDelivary_free() {
        return delivary_free;
    }

    public void setDelivary_free(int delivary_free) {
        this.delivary_free = delivary_free;
    }

    public String getRest_Image() {
        return Rest_Image;
    }

    public void setRest_Image(String rest_Image) {
        Rest_Image = rest_Image;
    }

    public String getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
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

    public String getRestaurants_name() {
        return Restaurants_name;
    }

    public void setRestaurants_name(String restaurants_name) {
        Restaurants_name = restaurants_name;
    }

    public String getSub_menu() {
        return sub_menu;
    }

    public void setSub_menu(String sub_menu) {
        this.sub_menu = sub_menu;
    }


    public String getDelivary_time() {
        return Delivary_time;
    }

    public void setDelivary_time(String delivary_time) {
        Delivary_time = delivary_time;
    }


}
