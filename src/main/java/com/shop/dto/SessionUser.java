package com.shop.dto;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.shop.entity.User;

import java.io.Serializable;

public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
    public SessionUser() {
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPicture(){
        return picture;
    }
    public void setPicture(String picture) { this.picture = picture; }
}
