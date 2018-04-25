package com.example.qinyu.doubanread.model;

import java.util.List;

/**
 * Created by Qinyu on 2018/4/16.
 */

public interface IBookModel {
    void setListData(String list);
    void setDetailData(String detail);
    List<BookIntro> getListData();
    BookDetail getDetailData();
}
