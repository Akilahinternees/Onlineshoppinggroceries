package com.example.userdashboardactivity;

public class Allorders {
    String useremail,key;

    public Allorders(String useremail, String key) {
        this.useremail = useremail;
        this.key = key;
    }

    public Allorders() {
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
