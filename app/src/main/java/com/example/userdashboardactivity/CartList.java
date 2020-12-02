package com.example.userdashboardactivity;

public class CartList {
    String user_email,ProductName,ProductQuantity,ProductPrice,TotalPrice,Key;

    public CartList(String user_email, String productName, String productQuantity, String productPrice, String totalPrice, String key) {
        this.user_email = user_email;
        this.ProductName = productName;
        this.ProductQuantity = productQuantity;
        this.ProductPrice = productPrice;
        this.TotalPrice = totalPrice;
        this.Key = key;
    }

    public CartList() {
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
