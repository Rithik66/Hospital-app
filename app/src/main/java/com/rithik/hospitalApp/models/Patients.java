package com.rithik.hospitalApp.models;

public class Patients {
    String id;
    String name;
    String location;
    String doctor;
    String stats;
    String profilePic;
    String emergencyContacts;
    String address;
    String blood;
    String dob;

    public Patients(String id, String name, String location, String doctor, String stats, String profilePic, String emergencyContacts, String address, String blood, String dob) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.doctor = doctor;
        this.stats = stats;
        this.profilePic = profilePic;
        this.emergencyContacts = emergencyContacts;
        this.address = address;
        this.blood = blood;
        this.dob = dob;
    }

    Patients(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(String emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
