package com.example.qinyu.doubanread.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qinyu.doubanread.R;
import com.example.qinyu.doubanread.model.BookDetail;
import com.example.qinyu.doubanread.model.BookDetailDao;
import com.example.qinyu.doubanread.presenter.AppDataBase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectFragment extends Fragment {
    BookDetailDao dao;
    List<BookDetail> list;
    AlertDialog alertDialog;
    RecyclerView recyclerView;
    Button change;
    int p=0;
    boolean flag=true;

    public CollectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_collect, container, false);

        //use Room
        dao=AppDataBase.getInstance(getActivity()).bookDetailDao();

        initalertdialog();

        recyclerView=view.findViewById(R.id.collect_list);
        change=view.findViewById(R.id.btn_change);
        initView();

        // Inflate the layout for this fragment
        return view;
    }

    private void initView(){
        list=dao.getall();
        CollectAdapter adapter=new CollectAdapter(getActivity(),list);
        adapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("bookDetail",list.get(position));
                startActivity(intent);
            }
            @Override
            public void OnItemLongClick(View view, int position) {
                alertDialog.show();
                p=position;
            }
        });
        final GridLayoutManager gridManager=new GridLayoutManager(getActivity(),3);
        final LinearLayoutManager linearManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(gridManager);
        recyclerView.setAdapter(adapter);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    recyclerView.setLayoutManager(linearManager);
                    change.setBackgroundResource(R.drawable.ic_grid);
                    flag=false;
                }else{
                    recyclerView.setLayoutManager(gridManager);
                    change.setBackgroundResource(R.drawable.ic_linear);
                    flag=true;
                }
            }
        });
    }

    private void initalertdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("确定取消收藏吗?");
        builder.setCancelable(true);
        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dao.delete(list.get(p));
                initView();
            }
        });
        builder.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog=builder.create();
    }

}
