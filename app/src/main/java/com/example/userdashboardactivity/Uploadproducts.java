package com.example.userdashboardactivity;

public class Uploadproducts {
    private String name,description,price,image_url,key;

    public Uploadproducts(String name, String description, String price, String image_url, String key) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.key = key;
    }

    public Uploadproducts() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
