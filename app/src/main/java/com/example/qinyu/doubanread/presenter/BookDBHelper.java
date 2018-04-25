package com.example.qinyu.doubanread.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qinyu.doubanread.model.BookDetail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qinyu on 2018/4/23.
 */

public class BookDBHelper extends SQLiteOpenHelper {
    private static int VERSION=1;
    private final static String DB_NAME="BOOK";
    private static BookDBHelper dbHelper;

    public static BookDBHelper getInstace(Context context){
        if(dbHelper==null)
            dbHelper=new BookDBHelper(context);
        return dbHelper;
    }

    public BookDBHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_BOOK="create table if not exists book(isbn char(13) primary key,tag text,author text,bookclass blob)";
        sqLiteDatabase.execSQL(CREATE_TABLE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(BookDetail book){
        ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(book);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            String INSERT="insert into book(isbn,tag,author,bookclass) values ('"
                    +book.getIsbn()+"','"
                    +book.getTag()+"','"
                    +book.getAuther()+"',"
                    +"?)";
            database.execSQL(INSERT,new Object[]{data});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(String isbn){
        String DELETE="delete from book where isbn="+isbn;
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        database.execSQL(DELETE);
        database.close();
    }

    public boolean exist(String isbn){
        String EXIST="select * from book where isbn="+isbn;
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery(EXIST,null);
        return cursor.moveToFirst();
    }

    public List<BookDetail> getall(){
        List<BookDetail> list=new ArrayList();
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from book",null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                byte data[]=cursor.getBlob(cursor.getColumnIndex("bookclass"));
                ByteArrayInputStream arrayInputStream=new ByteArrayInputStream(data);
                try {
                    ObjectInputStream objectInputStream=new ObjectInputStream(arrayInputStream);
                    BookDetail bookDetail=(BookDetail)objectInputStream.readObject();
                    objectInputStream.close();
                    arrayInputStream.close();
                    list.add(bookDetail);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        database.close();
        return list;
    }

}
