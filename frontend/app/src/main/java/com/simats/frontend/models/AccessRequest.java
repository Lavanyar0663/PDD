package com.simats.frontend.models;

import java.io.Serializable;

public class AccessRequest implements Serializable {
    private String id;
    private String name;
    private String role;
    private String department;
    private String timeAgo;
    private String status;
    private String phone;

    public AccessRequest(String id, String name, String role, String department, String timeAgo, String status) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.department = department;
        this.timeAgo = timeAgo;
        this.status = status;
        this.phone = "+91 9876543210"; // Mock data for profile
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public String getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }
}
