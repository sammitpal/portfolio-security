package com.security.dto;

public class UserResponse {

    private int id;
    private String username;
    private String email;
    private boolean active;
    private String roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public UserResponse(String username, String email, boolean active, String roles) {
        this.username = username;
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    public UserResponse() {
    }
}
