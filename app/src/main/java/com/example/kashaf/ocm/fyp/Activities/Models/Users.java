package com.example.kashaf.ocm.fyp.Activities.Models;

public class Users {

    private String key;
    private String name;
    private String email;
    private String phoneNo;
    private String profile_url;
    private String status;

    public Users() {

    }

    public Users(String key, String name, String email, String phoneNo, String profile_url, String status) {
        this.key = key;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.profile_url = profile_url;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
