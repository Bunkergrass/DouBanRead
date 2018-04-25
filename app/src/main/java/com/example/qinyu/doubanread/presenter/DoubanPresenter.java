package com.example.qinyu.doubanread.presenter;

import android.util.Log;

import com.example.qinyu.doubanread.model.IBookModel;
import com.example.qinyu.doubanread.model.IBookModelImpl;
import com.example.qinyu.doubanread.view.IBookView;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Qinyu on 2018/4/16.
 */

public class DoubanPresenter {
    IBookView iBookView;
    IBookModel iBookModel;

    public DoubanPresenter(IBookView view){
        iBookView=view;
        iBookModel=new IBookModelImpl();
    }

    public void requestBookList(String target, final int start){
        String url=new String("https://api.douban.com/v2/book/search?q="+target+"&start="+start);
        OkHttpGet.HttpGet(url, target, new OkHttpGet.DataCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                iBookView.showFailure(start);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                iBookModel.setListData(result);
                iBookView.onListUpdate(iBookModel.getListData(),start);//inform view
                //download bitmap
            }
        });
    }

    public void requestBookDetail(String isbn){
        String url=new String("https://api.douban.com/v2/book/isbn/:"+isbn);
        OkHttpGet.HttpGet(url, isbn, new OkHttpGet.DataCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                iBookView.showFailure(0);
            }

            @Override
            public void onSuccess(String result) throws Exception {
                iBookModel.setDetailData(result);
                iBookView.showBookDetail(iBookModel.getDetailData());
            }

        });
    }
}
