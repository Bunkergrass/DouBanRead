package com.example.qinyu.doubanread.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.qinyu.doubanread.view.StatFragment;

import java.util.List;

/**
 * Created by Qinyu on 2018/4/27.
 */

@Dao
public interface BookDetailDao {

    @Query("select * from book")
    List<BookDetail> getall();

    @Query("select * from book where isbn= :isbn")
    BookDetail exist(String isbn);

    @Query("select distinct tag,count(*) as count from book group by tag order by count desc")
    List<StatFragment.Times> stat();

    @Delete
    int delete(BookDetail bookDetail);

    @Insert
    Long insert(BookDetail bookDetail);
}
