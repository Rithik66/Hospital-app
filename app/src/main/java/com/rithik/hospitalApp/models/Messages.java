package com.rithik.hospitalApp.models;

public class Messages {

    String id;
    String message;
    boolean status;
    String sent;

    public Messages(String id, String message, boolean status, String sent) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.sent = sent;
    }

    public Messages(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", sent='" + sent + '\'' +
                '}';
    }
}