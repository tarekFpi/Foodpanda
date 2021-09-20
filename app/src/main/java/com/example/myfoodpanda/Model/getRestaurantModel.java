package com.example.myfoodpanda.Model;

public class getRestaurantModel {
    private String AreaName;
    private String CategroyName;
    private String RestaurantName;
    private String Restaurant_Image;

    public getRestaurantModel(String areaName, String categroyName, String restaurantName, String restaurant_Image) {
        AreaName = areaName;
        CategroyName = categroyName;
        RestaurantName = restaurantName;
        Restaurant_Image = restaurant_Image;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getCategroyName() {
        return CategroyName;
    }

    public void setCategroyName(String categroyName) {
        CategroyName = categroyName;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getRestaurant_Image() {
        return Restaurant_Image;
    }

    public void setRestaurant_Image(String restaurant_Image) {
        Restaurant_Image = restaurant_Image;
    }
}
