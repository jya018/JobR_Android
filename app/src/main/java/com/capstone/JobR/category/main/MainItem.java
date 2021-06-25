package com.capstone.JobR.category.main;

public class MainItem {

    String name;
    String content;
    String date;

    public MainItem(String name, String content, String date) {
        this.name = name;
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
