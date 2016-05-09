package com.epicodus.myrestaurants.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Restaurant {
    String name;
    String phone;
    String website;
    double rating;
    String imageUrl;
    List<String> address = new ArrayList<>();
    double latitude;
    double longitude;
    List<String> categories = new ArrayList<>();
    private String pushId;

    public Restaurant() {}

    public Restaurant(String name, String phone, String website,
                      double rating, String imageUrl, ArrayList<String> address,
                      double latitude, double longitude, ArrayList<String> categories) {
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.rating = rating;
        this.imageUrl = getLargeImageURL(imageUrl);
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return  website;
    }

    public double getRating() {
        return rating;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public List<String> getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getPushId() { return pushId; }

    public void setPushId(String pushId) { this.pushId = pushId; }

    public String getLargeImageURL(String imageUrl) {
        if (imageUrl.equals("https://s-media-cache-ak0.pinimg.com/736x/68/d4/c1/68d4c1092837a068a2fe65b8a29b867e.jpg")) {
            return imageUrl;
        }
        else {
            String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
            return largeImageUrl;
        }
    }
}
