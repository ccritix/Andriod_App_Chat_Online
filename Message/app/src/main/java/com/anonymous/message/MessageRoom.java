package com.anonymous.message;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MessageRoom")
public class MessageRoom {
    @PrimaryKey (autoGenerate = true)
    private int idmes;
    private String user1, user2, message1, message2, time;

    public MessageRoom(String user1, String user2, String message1, String message2, String time, int idmes) {
        this.user1 = user1;
        this.user2 = user2;
        this.message1 = message1;
        this.message2 = message2;
        this.time = time;
        this.idmes = idmes;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIdmes() {
        return idmes;
    }

    public void setIdmes(int idmes) {
        this.idmes = idmes;
    }
}
