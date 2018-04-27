package com.example.qinyu.doubanread.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.qinyu.doubanread.R;
import com.example.qinyu.doubanread.model.BookIntro;
import com.example.qinyu.doubanread.presenter.BitmapManager;
import com.example.qinyu.doubanread.presenter.OkHttpGet;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

import static com.example.qinyu.doubanread.R.*;

/**
 * Created by Qinyu on 2018/4/17.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<BookIntro> list;
    Context context;

    private static final int FOOTER=1;
    private static final int NORMAL=0;

    public BookAdapter(Context context,List<BookIntro> list) {
        this.context=context;
        this.list = list;
    }

    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }

    public OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void addList(List<BookIntro> newlist){
        list.remove(list.size()-1);
        notifyItemRemoved(list.size());//delete old footer
        list.addAll(newlist);
        notifyItemInserted(getItemCount()-1);
        setFooter();
    }

    public void setFooter(){
        notifyItemInserted(getItemCount()-1);
        list.add(new BookIntro());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemimg;
        TextView line1;
        RatingBar ratingBar;
        TextView line2;
        TextView line3;

        public ViewHolder(View itemView) {
            super(itemView);
            itemimg=itemView.findViewById(id.item_img);
            line1=itemView.findViewById(id.tvline1);
            ratingBar=itemView.findViewById(id.ratingBar);
            line2=itemView.findViewById(id.tvline2);
            line3=itemView.findViewById(id.tvline3);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount()-1)
            return FOOTER;
        else
            return NORMAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == FOOTER){
            View view= LayoutInflater.from(parent.getContext()).inflate(layout.book_list_footer,parent,false);
            return new ViewHolder(view);
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(layout.book_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(position==getItemCount()-1){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.OnItemClick(view,position);
                    }
                }
            });
            return;
        }

        holder.line1.setText(list.get(position).getTitle());
        holder.ratingBar.setRating(Float.valueOf(list.get(position).getAverage())/2);
        holder.line2.setText("评分:"+list.get(position).getAverage()+" / "+list.get(position).getNumrate()+"人评价");
        holder.line3.setText(list.get(position).getAuther()+"/"+list.get(position).getPublisher()+"/"
                +list.get(position).getPrice()+"/isbn:"+list.get(position).getIsbn());

        Glide.with(context).load(list.get(position).getImgurl()).placeholder(mipmap.ic_launcher).into(holder.itemimg);//load image by glide
        //BitmapManager.with(context).load(list.get(position).getImgurl()).into(holder.itemimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.OnItemClick(view,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
