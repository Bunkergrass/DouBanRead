package com.example.qinyu.doubanread.view;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qinyu.doubanread.R;
import com.example.qinyu.doubanread.presenter.BookDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatFragment extends Fragment {

    public StatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_stat, container, false);

        //search on sql
        List<Times> times = statTag();


//        ArcView arcView=view.findViewById(R.id.arcview);
//        ArcView.ArcViewAdapter myAdapter = arcView.new ArcViewAdapter<Times>(){
//            @Override
//            public double getValue(Times times) {
//                return times.hour;
//            }
//
//            @Override
//            public String getText(Times times) {
//                return times.text;
//            }
//        };
//        myAdapter.setData(times);//绑定数据
//        arcView.setMaxNum(4);

        BarView barView=view.findViewById(R.id.barView);
        BarView.BarViewAdapter myAdapter = barView.new BarViewAdapter<Times>() {
            @Override
            public double getValue(Times times) {
                return times.hour;
            }

            @Override
            public String getText(Times times) {
                return times.text;
            }
        };
        myAdapter.setData(times);

        // Inflate the layout for this fragment
        return view;
    }

    class Times{
        double hour;
        String text;
    }
    private List<Times> statTag(){
        List<Times> list=new ArrayList<>();
        SQLiteDatabase database=BookDBHelper.getInstace(getActivity()).getReadableDatabase();
        Cursor cursor=database.rawQuery("select distinct tag,count(*) as count from book group by tag order by count desc",null);
        while (cursor.moveToNext()){
            Times t=new Times();
            t.hour=cursor.getDouble(cursor.getColumnIndex("count"));
            t.text=cursor.getString(cursor.getColumnIndex("tag"));
            list.add(t);
            Log.d("time",t.text+" "+t.hour);
        }
        return list;
    }

}
