package com.simats.frontend.models;

import java.io.Serializable;

public class Patient implements Serializable {
    private String name;
    private String departmentBadge;
    private String pid;
    private String ageGender;
    private String lastVisit;

    public Patient(String name, String departmentBadge, String pid, String ageGender, String lastVisit) {
        this.name = name;
        this.departmentBadge = departmentBadge;
        this.pid = pid;
        this.ageGender = ageGender;
        this.lastVisit = lastVisit;
    }

    public String getName() {
        return name;
    }

    public String getDepartmentBadge() {
        return departmentBadge;
    }

    public String getPid() {
        return pid;
    }

    public String getAgeGender() {
        return ageGender;
    }

    public String getLastVisit() {
        return lastVisit;
    }
}
