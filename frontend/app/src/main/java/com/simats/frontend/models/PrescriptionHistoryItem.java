package com.simats.frontend.models;

public class PrescriptionHistoryItem {
    private String name;
    private String initials;
    private String opdId;
    private String phone;
    private String diagnosis;
    private String drugs;
    private String time;
    private String status;

    public PrescriptionHistoryItem(String name, String initials, String opdId, String phone, String diagnosis,
            String drugs, String time, String status) {
        this.name = name;
        this.initials = initials;
        this.opdId = opdId;
        this.phone = phone;
        this.diagnosis = diagnosis;
        this.drugs = drugs;
        this.time = time;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getInitials() {
        return initials;
    }

    public String getOpdId() {
        return opdId;
    }

    public String getPhone() {
        return phone;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getDrugs() {
        return drugs;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
