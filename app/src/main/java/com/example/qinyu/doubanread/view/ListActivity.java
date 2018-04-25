package com.example.qinyu.doubanread.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qinyu.doubanread.R;
import com.example.qinyu.doubanread.model.BookDetail;
import com.example.qinyu.doubanread.model.BookIntro;
import com.example.qinyu.doubanread.presenter.DoubanPresenter;

import java.util.List;

public class ListActivity extends AppCompatActivity implements IBookView{

    DoubanPresenter doubanPresenter;
    RecyclerView recyclerView;
    String target;

    EditText etSearch;
    Button btnRefresh;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();

        target=getIntent().getStringExtra("target");
        doubanPresenter.requestBookList(target,0);

    }

    private void init(){
        doubanPresenter=new DoubanPresenter(ListActivity.this);
        recyclerView=findViewById(R.id.recyclerview);
        etSearch=findViewById(R.id.et_search);
        Button btnSearch=findViewById(R.id.btn_search);
        tvError=findViewById(R.id.tv_error);
        btnRefresh=findViewById(R.id.btn_refresh);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target=etSearch.getText().toString();
                doubanPresenter.requestBookList(target,0);
            }
        });
    }

    BookAdapter adapter;
    @Override
    public void onListUpdate(final List<BookIntro> list, final int start) {
        recyclerView.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);
        if(start>=20){
            adapter.addList(list);
        }
        else{
            adapter=new BookAdapter(ListActivity.this,list);
            adapter.setFooter();
            adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    if(position == adapter.getItemCount()-1){
                        doubanPresenter.requestBookList(target,start+20);
                    }else{
                        String isbn=list.get(position).getIsbn();
                        Intent intent=new Intent(ListActivity.this,DetailActivity.class);
                        intent.putExtra("isbn",isbn);
                        startActivity(intent);
                    }
                }
            });
            LinearLayoutManager layoutManager=new LinearLayoutManager(ListActivity.this, LinearLayout.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//分割线
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showFailure(int start) {
        if(start<20){
            recyclerView.setVisibility(View.GONE);
            btnRefresh.setVisibility(View.VISIBLE);
        }
        tvError.setVisibility(View.VISIBLE);
        Toast.makeText(ListActivity.this,"Sorry",Toast.LENGTH_SHORT).show();
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doubanPresenter.requestBookList(target,0);
            }
        });
    }
    @Override
    public void showBookDetail(BookDetail bookDetail) {

    }
}
