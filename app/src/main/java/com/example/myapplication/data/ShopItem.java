package com.example.myapplication.data;

import java.io.Serializable;

public class ShopItem implements Serializable {

    public ShopItem(String title,String author,String year,int resourceId) {
        this.title=title;
        this.author = author;
        this.year=year;
        this.resourceId=resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }



    public String getTitle() {
        return title;
    }
    public String getYear(){ return year;  }

    public void setTitle(String title) {
        this.title = title;
    }
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String author;
    private int resourceId;
    private String year;

}
