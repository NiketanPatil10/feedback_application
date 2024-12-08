package com.example.interviewtest;

public class Feedback {

    public String userfeedback;
    private String id;
    private String date;

    public Feedback() {
    }

    public Feedback(String userfeedback, String id, String date) {
        this.userfeedback = userfeedback;
        this.id = id;
        this.date = date;
    }

    public String getUserfeedback() {
        return userfeedback;
    }

    public void setUserfeedback(String userfeedback) {
        this.userfeedback = userfeedback;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
