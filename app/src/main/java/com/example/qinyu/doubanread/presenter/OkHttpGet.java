package com.example.qinyu.doubanread.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Qinyu on 2018/4/16.
 */

public class OkHttpGet {
    private static OkHttpGet okhttpGet;
    private static OkHttpClient okHttpClient;
    private Handler mhandler;

    private OkHttpGet(){
        okHttpClient=new OkHttpClient();
        mhandler=new Handler(Looper.getMainLooper());
    }

    private static OkHttpGet getInstance(){
        if(okhttpGet==null){
            okhttpGet=new OkHttpGet();
        }
        return okhttpGet;
    }

    public static void HttpGet(String url,String param,DataCallback callback){
        getInstance().InnerGet(url,param,callback);
    }

    private void InnerGet(String url, String param, final DataCallback callback){
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                GetFailure(request,e,callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                GetSuccess(result,callback);
            }
        });
    }

    public interface DataCallback{
        void onFailure(Request request, IOException e);
        void onSuccess(String result) throws Exception;
    }

    private void GetFailure(final Request request,final IOException e, final DataCallback callback){
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if(callback!=null)
                    callback.onFailure(request, e);
            }
        });
    }

    private void GetSuccess(final String result, final DataCallback callback){
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if(callback!=null)
                    try {
                        callback.onSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    public interface BitmapCallback{
        void onFailure(Request request, IOException e);
        void onSuccess(Bitmap result) throws Exception;
    }

    public static void GetBitmap(String url,BitmapCallback callback){
        getInstance().InnerGetBitmap(url,callback);
    }

    private void InnerGetBitmap(String url,final BitmapCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                GetFailure(request,e,callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] result=response.body().bytes();
                Bitmap bitmap= BitmapFactory.decodeByteArray(result,0,result.length);
                GetSuccess(bitmap,callback);
            }
        });
    }

    private void GetSuccess(final Bitmap result, final BitmapCallback callback){
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if(callback!=null)
                    try {
                        callback.onSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }
    private void GetFailure(final Request request,final IOException e, final BitmapCallback callback){
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if(callback!=null)
                    callback.onFailure(request, e);
            }
        });
    }


}




