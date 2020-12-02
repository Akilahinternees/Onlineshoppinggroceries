package com.example.userdashboardactivity;

public class Level {
    String user_id,level,email,phone;

    public Level(String user_id, String level, String email) {
        this.user_id = user_id;
        this.level = level;
        this.email = email;


    }

    public Level() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
