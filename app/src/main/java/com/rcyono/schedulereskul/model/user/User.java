package com.rcyono.schedulereskul.model.user;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("password")
    private String password;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("update_at")
    private String updateAt;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}