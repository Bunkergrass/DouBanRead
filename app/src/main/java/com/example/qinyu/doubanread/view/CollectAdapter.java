package com.example.qinyu.doubanread.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qinyu.doubanread.R;
import com.example.qinyu.doubanread.model.BookDetail;

import java.util.List;

/**
 * Created by Qinyu on 2018/4/21.
 */

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {
    private List<BookDetail> list;
    Context context;

    public CollectAdapter(Context context,List<BookDetail> list){
        this.context=context;
        this.list=list;
    }

    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
        void OnItemLongClick(View view,int position);
    }

    public OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_collect;
        TextView tv_title;

        public ViewHolder(View itemView){
            super(itemView);
            img_collect=itemView.findViewById(R.id.img_collect);
            tv_title=itemView.findViewById(R.id.tv_title);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_collect,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_title.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getImgurl()).into(holder.img_collect);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(view,position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.OnItemLongClick(view,position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
