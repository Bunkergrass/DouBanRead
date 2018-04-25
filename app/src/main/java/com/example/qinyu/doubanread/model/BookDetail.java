package com.example.qinyu.doubanread.model;

import java.io.Serializable;

/**
 * Created by Qinyu on 2018/4/16.
 */

public class BookDetail implements Serializable{
    private String imgurl;
    private String title;
    private String alt_title;
    private String grade;
    private String numrate;
    private String tag;
    private String isbn;
    private String auther;
    private String pages;
    private String pulisher;
    private String author_intro;
    private String summary;

    public BookDetail(String imgurl, String title, String alt_title, String grade, String numrate,String tag, String isbn, String auther, String pages, String pulisher, String author_intro, String summary) {
        this.imgurl = imgurl;
        this.title = title;
        this.alt_title = alt_title;
        this.grade = grade;
        this.numrate = numrate;
        this.tag=tag;
        this.isbn = isbn;
        this.auther = auther;
        this.pages = pages;
        this.pulisher = pulisher;
        this.author_intro = author_intro;
        this.summary = summary;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getTitle() {
        return title;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public String getGrade() {
        return grade;
    }

    public String getNumrate() {
        return numrate;
    }

    public String getTag() {
        return tag;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuther() {
        return auther;
    }

    public String getPages() {
        return pages;
    }

    public String getPulisher() {
        return pulisher;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public String getSummary() {
        return summary;
    }
}
