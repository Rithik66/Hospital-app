package com.rithik.hospitalApp.models;

import java.util.ArrayList;

public class Chats {
    String id;
    ArrayList<Messages> messages;
    String lastMessage;
    String lastMessageTime;
    String lastMessageName;

    public Chats(String id, ArrayList<Messages> messages, String lastMessage, String lastMessageTime, String lastMessageName) {
        this.id = id;
        this.messages = messages;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        this.lastMessageName = lastMessageName;
    }
    Chats(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Messages> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Messages> messages) {
        this.messages = messages;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getLastMessageName() {
        return lastMessageName;
    }

    public void setLastMessageName(String lastMessageName) {
        this.lastMessageName = lastMessageName;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", messages=" + messages +
                ", lastMessage='" + lastMessage + '\'' +
                ", lastMessageTime='" + lastMessageTime + '\'' +
                ", lastMessageName='" + lastMessageName + '\'' +
                '}';
    }
}
