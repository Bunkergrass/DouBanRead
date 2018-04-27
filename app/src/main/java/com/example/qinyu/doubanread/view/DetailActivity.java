package com.example.qinyu.doubanread.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qinyu.doubanread.R;
import com.example.qinyu.doubanread.model.BookDetail;
import com.example.qinyu.doubanread.model.BookDetailDao;
import com.example.qinyu.doubanread.model.BookIntro;
import com.example.qinyu.doubanread.presenter.AppDataBase;
import com.example.qinyu.doubanread.presenter.DoubanPresenter;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements IBookView{
    DoubanPresenter doubanPresenter;

    BookDetailDao dao;

    String isbn;
    ImageButton collect;
    TextView title;
    RatingBar ratingBar;
    TextView grade;
    TextView information;
    TextView summary;
    TextView author;
    TextView expand1;
    TextView expand2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        isbn=getIntent().getStringExtra("isbn");
        BookDetail bookDetail=(BookDetail) getIntent().getSerializableExtra("bookDetail");

        dao=AppDataBase.getInstance(this).bookDetailDao();

        initView();

        if(isbn!=null){ //where be started from
            doubanPresenter=new DoubanPresenter(DetailActivity.this);
            doubanPresenter.requestBookDetail(isbn);
        }else if(bookDetail!=null){
            isbn=bookDetail.getIsbn();
            showBookDetail(bookDetail);
            Log.d("detail",bookDetail.getSummary());
        }

        if(dao.exist(isbn) != null  ) { //if exits in sql
            collect.setBackgroundResource(R.drawable.ic_collected);
        }else{
            collect.setBackgroundResource(R.drawable.ic_collect);
        }

    }

    private void initView(){
        title=findViewById(R.id.tv_title);
        ratingBar=findViewById(R.id.ratingBar);
        grade=findViewById(R.id.tv_grade);
        collect=findViewById(R.id.btn_collect);
        information=findViewById(R.id.tv_inform);
        summary=findViewById(R.id.tv_summary);
        author=findViewById(R.id.tv_author);

        expand1=findViewById(R.id.expand1);
        expand2=findViewById(R.id.expand2);

    }

    @Override
    public void onListUpdate(List<BookIntro> list,int start) {
        //
    }

    @Override
    public void showBookDetail(final BookDetail bookDetail) {
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dao.exist(isbn) != null){
                    dao.delete(bookDetail);
                    collect.setBackgroundResource(R.drawable.ic_collect);
                }else{
                    dao.insert(bookDetail);
                    collect.setBackgroundResource(R.drawable.ic_collected);
                }
            }
        });

        if(bookDetail.getAlt_title().isEmpty())
            title.setText(bookDetail.getTitle());
        else
            title.setText(bookDetail.getTitle()+"--"+bookDetail.getAlt_title());
        ratingBar.setRating(Float.valueOf(bookDetail.getGrade())/2);
        grade.setText(bookDetail.getGrade()+" / "+bookDetail.getNumrate()+" 人评价");

        String inform=bookDetail.getAuther()+"著 / ";
        if(!bookDetail.getPulisher().isEmpty())
            inform=inform+bookDetail.getPulisher()+" / ";
        if(!bookDetail.getPages().isEmpty())
            inform=inform+bookDetail.getPages()+"页 /";
        inform=inform+" isbn:"+bookDetail.getIsbn();
        information.setText(inform);

        summary.setText(bookDetail.getSummary());
        author.setText(bookDetail.getAuthor_intro());

        if(summary.getLineCount()>summary.getMaxLines())
            expand1.setVisibility(View.VISIBLE);
        if(author.getLineCount()>author.getMaxLines())
            expand2.setVisibility(View.VISIBLE);
        expand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                summary.setMaxLines(Integer.MAX_VALUE);
                expand1.setVisibility(View.GONE);
            }
        });
        expand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                author.setMaxLines(Integer.MAX_VALUE);
                expand2.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showFailure(int start) {
        Toast.makeText(DetailActivity.this,"Sorry",Toast.LENGTH_SHORT).show();
    }
}
