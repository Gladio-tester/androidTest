package com.aquafadas.tag_the_bus.entities;

/**
 * Created by Yahya on 04/10/2016.
 */

public class Picture {

    private String image;
    private String title;
    private String idStreet;
    private String date;
    public Picture() {
    }

    public Picture(String image, String title, String idStreet, String date) {
        this.image = image;
        this.title = title;
        this.idStreet = idStreet;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdStreet() {
        return idStreet;
    }

    public void setIdStreet(String idStreet) {
        this.idStreet = idStreet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
