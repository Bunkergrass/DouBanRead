package com.example.qinyu.doubanread.model;

/**
 * Created by Qinyu on 2018/4/16.
 */

public class BookIntro {
    private String imgurl;
    private String isbn;
    private String title;
    private String auther;
    private String publisher;
    private String price;
    private String numrate;
    private String average;

    public BookIntro(){
        this.imgurl = "";
        this.isbn = "";
        this.title = "";
        this.auther = "";
        this.publisher = "";
        this.price = "";
        this.numrate = "";
        this.average = "";
    }

    public BookIntro(String imgurl, String isbn, String title, String auther, String publisher, String price, String numrate, String average) {
        this.imgurl = imgurl;
        this.isbn = isbn;
        this.title = title;
        this.auther = auther;
        this.publisher = publisher;
        this.price = price;
        this.numrate = numrate;
        this.average = average;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuther() {
        return auther;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPrice() {
        return price;
    }

    public String getNumrate() {
        return numrate;
    }

    public String getAverage() {
        return average;
    }
}
