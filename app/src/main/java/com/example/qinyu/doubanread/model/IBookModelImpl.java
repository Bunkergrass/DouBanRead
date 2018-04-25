package com.example.qinyu.doubanread.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qinyu on 2018/4/16.
 */

public class IBookModelImpl implements IBookModel {
    BookDetail bookDetail;
    List<BookIntro> bookIntroList;

    @Override
    public void setListData(String result) {
        bookIntroList=new ArrayList();

        try {
            JSONArray jsonArray=new JSONObject(result).getJSONArray("books");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject!=null){
                    String imgurl=jsonObject.optString("image");
                    String isbn=jsonObject.optString("isbn13");
                    String title=jsonObject.optString("title");
                    String author=jsonObject.optString("author");
                    String publisher=jsonObject.optString("publisher");
                    String price=jsonObject.optString("price");
                    JSONObject rating=jsonObject.getJSONObject("rating");
                    String numrate=String.valueOf(rating.optInt("numRaters"));
                    String avverage=rating.optString("average");
                    bookIntroList.add(new BookIntro(imgurl,isbn,title,author,publisher,price,numrate,avverage) );
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setDetailData(String detail) {
        try {
            JSONObject jsonObject=new JSONObject(detail);
            String imgurl=jsonObject.optString("image");
            String title=jsonObject.optString("title");
            String alt_title=jsonObject.optString("alt_title");

            JSONObject rating=jsonObject.getJSONObject("rating");
            String numrate=String.valueOf(rating.optInt("numRaters"));
            String grade=rating.optString("average");

            JSONArray jsonArray=jsonObject.getJSONArray("tags");
            String tag=jsonArray.getJSONObject(0).optString("title");

            String isbn=jsonObject.optString("isbn13");
            String author=jsonObject.optString("author");
            String pages=jsonObject.optString("pages");
            String publisher=jsonObject.optString("publisher");
            String author_intro=jsonObject.optString("author_intro");
            String summary=jsonObject.optString("summary");

            bookDetail=new BookDetail(imgurl,title,alt_title,grade,numrate,tag,isbn,author,pages,publisher,author_intro,summary);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<BookIntro> getListData() {
        if(bookIntroList!=null)
            return bookIntroList;
        else return null;
    }

    @Override
    public BookDetail getDetailData() {
        if(bookDetail!=null)
            return bookDetail;
        else return null;
    }
}
