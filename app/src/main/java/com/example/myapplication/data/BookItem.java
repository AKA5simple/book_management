package com.example.myapplication.data;

import java.io.Serializable;

public class BookItem implements Serializable {

    public BookItem(String title, String author, String year, String translator, String publisher, String putdate, String isbn, int resourceId) {
        this.title=title;
        this.author = author;
        this.year=year;
        this.translator=translator;
        this.publisher=publisher;
        this.putdate=putdate;
        this.isbn=isbn;
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

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    private String translator;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    private String publisher;

    public String getPutdate() {
        return putdate;
    }

    public void setPutdate(String putdate) {
        this.putdate = putdate;
    }

    private String putdate;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    private String isbn;

}
