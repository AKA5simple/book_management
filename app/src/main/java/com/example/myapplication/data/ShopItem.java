package com.example.myapplication.data;

public class ShopItem {

    public ShopItem(String title,Double price,int resourceId) {
        this.title=title;
        this.price = price;
        this.resourceId=resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    private String title;
    private Double price;
    private int resourceId;

}
