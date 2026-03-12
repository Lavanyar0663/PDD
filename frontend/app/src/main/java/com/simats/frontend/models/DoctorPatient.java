package com.simats.frontend.models;

public class DoctorPatient {
    private String id;
    private String name;
    private String initials;
    private String opdId;
    private String ageGender;
    private String lastVisit;
    private String phone;
    private int avatarColorResId;
    private int avatarTextColorResId;

    public DoctorPatient(String name, String initials, String opdId, String ageGender, String lastVisit, String phone,
            int avatarColorResId, int avatarTextColorResId) {
        this.name = name;
        this.initials = initials;
        this.opdId = opdId;
        this.ageGender = ageGender;
        this.lastVisit = lastVisit;
        this.phone = phone;
        this.avatarColorResId = avatarColorResId;
        this.avatarTextColorResId = avatarTextColorResId;
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

    public String getAgeGender() {
        return ageGender;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public String getPhone() {
        return phone;
    }

    public int getAvatarColorResId() {
        return avatarColorResId;
    }

    public int getAvatarTextColorResId() {
        return avatarTextColorResId;
    }
}
