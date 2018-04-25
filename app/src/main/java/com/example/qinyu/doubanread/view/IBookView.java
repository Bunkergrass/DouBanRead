package com.example.qinyu.doubanread.view;

import com.example.qinyu.doubanread.model.BookDetail;
import com.example.qinyu.doubanread.model.BookIntro;

import java.util.List;

/**
 * Created by Qinyu on 2018/4/16.
 */

public interface IBookView {
    void onListUpdate(List<BookIntro> list,int start);
    void showBookDetail(BookDetail bookDetail);
    void showFailure(int start);
}
