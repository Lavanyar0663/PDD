package com.simats.frontend.models;

import java.io.Serializable;

public class Pharmacist implements Serializable {
    private String id;
    private String name;
    private String status;

    public Pharmacist(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
