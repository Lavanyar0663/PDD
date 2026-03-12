package com.simats.frontend.models;

import java.io.Serializable;

public class Doctor implements Serializable {
    private String id;
    private String name;
    private String department;
    private String status;

    public Doctor(String id, String name, String department, String status) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getStatus() {
        return status;
    }
}
