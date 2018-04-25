package com.example.qinyu.doubanread.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.qinyu.doubanread.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_search, container, false);


        final EditText editText=view.findViewById(R.id.et_search);
        Button button=view.findViewById(R.id.btn_search);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String target=editText.getText().toString();
                Intent intent=new Intent(getActivity(), ListActivity.class);
                intent.putExtra("target",target);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

}
