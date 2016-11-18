package com.xjy.demo.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xjy.demo.Model;
import com.xjy.demo.R;
import com.xjy.demo.home.provider.BannerProvider;
import com.xjy.demo.home.provider.HomeItemProvider;
import com.xjy.demo.home.provider.NavProvider;
import com.xjy.jymultipleadapter.AbsHeaderFooterProvider;
import com.xjy.jymultipleadapter.MultipleAdapter;
import com.xjy.jymultipleadapter.MultipleViewHolder;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MultipleAdapter multipleAdapter = new MultipleAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 12));

        BannerProvider bannerProvider = new BannerProvider();
        multipleAdapter.registerProvider(bannerProvider);
        bannerProvider.add("banner");

        NavProvider navProvider = new NavProvider();
        navProvider.setSpanSize(4);
        multipleAdapter.registerProvider(navProvider);
        navProvider.add(new Model("nav1"));
        navProvider.add(new Model("nav2"));
        navProvider.add(new Model("nav3"));
        navProvider.add(new Model("nav4"));

        HomeItemProvider homeItemProvider = new HomeItemProvider();
        homeItemProvider.setSpanSize(3);
        homeItemProvider.add(new Model("item1"));
        homeItemProvider.add(new Model("item2"));
        homeItemProvider.add(new Model("item3"));
        homeItemProvider.add(new Model("item4"));
        homeItemProvider.add(new Model("item5"));
        homeItemProvider.add(new Model("item6"));
        homeItemProvider.add(new Model("item7"));
        homeItemProvider.add(new Model("item8"));
        homeItemProvider.registerHeaderProvider("Header", new AbsHeaderFooterProvider<String>() {
            @Override
            public int onInflateLayout() {
                return R.layout.item_home_header;
            }

            @Override
            public void onBindViewHolder(MultipleViewHolder viewHolder, int position, String item) {
                viewHolder.setText(R.id.textView, item);
            }
        });

        multipleAdapter.registerProvider(homeItemProvider);

        HomeItemProvider singleLineProvider = new HomeItemProvider();
        singleLineProvider.add(new Model("item1"));
        singleLineProvider.add(new Model("item2"));
        singleLineProvider.add(new Model("item3"));
        singleLineProvider.add(new Model("item4"));
        singleLineProvider.add(new Model("item5"));
        singleLineProvider.add(new Model("item6"));
        singleLineProvider.add(new Model("item7"));
        singleLineProvider.add(new Model("item8"));
        singleLineProvider.registerHeaderProvider("Header2", new AbsHeaderFooterProvider<String>() {
            @Override
            public int onInflateLayout() {
                return R.layout.item_home_header;
            }

            @Override
            public void onBindViewHolder(MultipleViewHolder viewHolder, int position, String item) {
                viewHolder.setText(R.id.textView, item);
            }
        });
        multipleAdapter.registerProvider(singleLineProvider);


        recyclerView.setAdapter(multipleAdapter);

        multipleAdapter.notifyDataSetChanged();

    }

}
