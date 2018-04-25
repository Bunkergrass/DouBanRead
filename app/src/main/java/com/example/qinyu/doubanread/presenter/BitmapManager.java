package com.example.qinyu.doubanread.presenter;

/**
 * Created by Qinyu on 2018/4/20.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.qinyu.doubanread.R;

import java.io.IOException;
import java.lang.ref.SoftReference;
import okhttp3.Request;

/**
 * Created by Qinyu on 2018/4/20.
 */

public class BitmapManager {
    private static Context context;
    private static BitmapManager manager;

    private static BitmapManager GetInstance(){
        if(manager == null)
            manager=new BitmapManager();
        return manager;
    }

    public static BitmapManager with(Context fragment){
        context=fragment;
        return GetInstance();
    }

    private LruCache<String,SoftReference<Bitmap>> BitmapCache=new LruCache<>(1024*1024*32);
    public class RequestCreator{
        private String url;

        public RequestCreator(String url){
            this.url=url;
        }
        public void into(final ImageView view){
            final SoftReference<Bitmap> reference=BitmapCache.get(url);
            if(reference!=null){
                view.setImageBitmap(reference.get());
                Log.d("TAG","load from cache");
            }else{
                OkHttpGet.GetBitmap(url, new OkHttpGet.BitmapCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        //
                    }
                    @Override
                    public void onSuccess(Bitmap result) throws Exception {
                        BitmapCache.put(url,new SoftReference(result));
                        Log.d("TAG","load from net");
                        view.setImageBitmap(result);
                    }
                });
            }
        }
    }

    public RequestCreator load(String url){
        return new RequestCreator(url);
    }
}
