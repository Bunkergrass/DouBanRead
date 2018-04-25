package com.example.qinyu.doubanread.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.qinyu.doubanread.R;

public class MainActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    private CollectFragment collectFragment;
    private StatFragment statFragment;
    private Fragment[] fragments;
    private int lastFragment=1;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_collect:
                    switchFragment(lastFragment,1);
                    lastFragment=1;
                    return true;
                case R.id.navigation_search:
                    switchFragment(lastFragment,0);
                    lastFragment=0;
                    return true;
                case R.id.navigation_stat:
                    switchFragment(lastFragment,2);
                    lastFragment=2;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation=findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        initFragment();
    }

    private void initFragment(){
        searchFragment=new SearchFragment();
        collectFragment=new CollectFragment();
        statFragment=new StatFragment();
        fragments=new Fragment[]{searchFragment,collectFragment,statFragment};
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout,collectFragment)
                .show(collectFragment)
                .commit();
    }

    public void switchFragment(int last,int next){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[last]);
        if(!fragments[next].isAdded())
            transaction.replace(R.id.frameLayout,fragments[next]);
        transaction.show(fragments[next]).commitAllowingStateLoss();
    }
}
