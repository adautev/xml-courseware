package com.dautev.courseware.xml.domain;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private final Date date;
    private String user;
    private String message;

    public Message(Date date, String user, String message) {
        this.user = user;
        this.date = date;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }
}
