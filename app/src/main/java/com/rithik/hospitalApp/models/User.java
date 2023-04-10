package com.rithik.hospitalApp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User{
    ArrayList<Chats> chats;
    Check check;
    private String currentStatus;
    private String email;
    private String id;
    private String profilePic;
    private String userName;

    private String contacts;

    User(){}
    public User(ArrayList<Chats> chats, Check check, String currentStatus, String email, String id, String profilePic, String userName, String contacts) {
        this.chats = chats;
        this.check = check;
        this.currentStatus = currentStatus;
        this.email = email;
        this.id = id;
        this.profilePic = profilePic;
        this.userName = userName;
        this.contacts = contacts;
    }

    public ArrayList<Chats> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chats> chats) {
        this.chats = chats;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public static class Check {
        private boolean in;
        private float time;

        public Check(boolean in, float time) {
            this.in = in;
            this.time = time;
        }
        Check(){}

        public boolean getIn() {
            return in;
        }

        public void setIn(boolean in) {
            this.in = in;
        }

        public float getTime() {
            return time;
        }

        public void setTime(float time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "{" +
                    "in=" + in +
                    ", time=" + time +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "User{" +
                "chats=" + chats +
                ", check=" + check +
                ", currentStatus='" + currentStatus + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", userName='" + userName + '\'' +
                ", contacts='" + contacts + '\'' +
                '}';
    }
}
