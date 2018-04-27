package com.example.qinyu.doubanread.presenter;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.qinyu.doubanread.model.BookDetail;
import com.example.qinyu.doubanread.model.BookDetailDao;

/**
 * Created by Qinyu on 2018/4/27.
 */

@Database(entities = {BookDetail.class} , version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract BookDetailDao bookDetailDao();

    private static AppDataBase appDataBase;

    private static final Object sLock = new Object();

    public static AppDataBase getInstance(Context context){
        synchronized (sLock){
            if(appDataBase==null)
                appDataBase = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"book.db")
                        .allowMainThreadQueries()
                        .build();
        }
        return appDataBase;
    }
}
