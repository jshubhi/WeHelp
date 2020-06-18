package com.example.wehelp;

public class UserProfile {
    public String username;
    public String type;
    public String phonenumber;
    public String email;

    public UserProfile(){

    }
    public UserProfile(String username, String type, String phonenumber, String email) {
        this.username = username;
        this.type = type;
        this.phonenumber = phonenumber;
        this.email=email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
