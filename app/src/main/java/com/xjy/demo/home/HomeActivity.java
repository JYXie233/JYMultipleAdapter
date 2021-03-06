package com.xjy.demo.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.xjy.demo.Model;
import com.xjy.demo.R;
import com.xjy.demo.home.provider.BannerProvider;
import com.xjy.demo.home.provider.HomeItemProvider;
import com.xjy.demo.home.provider.NavProvider;
import com.xjy.widget.AbsHeaderFooterProvider;
import com.xjy.widget.AbsItemProvider;
import com.xjy.widget.MultipleAdapter;
import com.xjy.widget.MultipleViewHolder;
import com.xjy.widget.OnProviderItemClickListener;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final MultipleAdapter multipleAdapter = new MultipleAdapter(this);

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

        AbsHeaderFooterProvider<String> header = new AbsHeaderFooterProvider<String>() {
            @Override
            public int onInflateLayout() {
                return R.layout.item_home_header;
            }

            @Override
            public void onBindViewHolder(MultipleViewHolder viewHolder, int position, String item) {
                viewHolder.setText(R.id.textView, item);
            }
        };

        header.setKeep(true);
        header.setOnProviderClickListener(new OnProviderItemClickListener<AbsHeaderFooterProvider<String>>() {
            @Override
            public void onProviderClick(AbsHeaderFooterProvider<String> provider, View view, int position) {
                Toast.makeText(HomeActivity.this, "header section:" + position, Toast.LENGTH_SHORT).show();
                multipleAdapter.toggleExpand(position);
            }
        });

        homeItemProvider.registerHeaderProvider("Header", header);

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
        singleLineProvider.add(new Model("item1"));
        singleLineProvider.add(new Model("item2"));
        singleLineProvider.add(new Model("item3"));
        singleLineProvider.add(new Model("item4"));
        singleLineProvider.add(new Model("item5"));
        singleLineProvider.add(new Model("item6"));
        singleLineProvider.add(new Model("item7"));
        singleLineProvider.add(new Model("item8"));
        singleLineProvider.registerHeaderProvider("Header2", header);
        multipleAdapter.registerProvider(singleLineProvider);


        recyclerView.setAdapter(multipleAdapter);

        multipleAdapter.notifyDataSetChanged();

        bannerProvider.setOnProviderClickListener(new OnProviderItemClickListener<AbsItemProvider<String, MultipleViewHolder>>() {
            @Override
            public void onProviderClick(AbsItemProvider<String, MultipleViewHolder> provider, View view, int position) {
                Toast.makeText(HomeActivity.this, provider.get(position), Toast.LENGTH_SHORT).show();
            }
        });


        homeItemProvider.setOnClickViewListener(R.id.textView, new OnProviderItemClickListener<AbsItemProvider<Model, MultipleViewHolder>>() {
            @Override
            public void onProviderClick(AbsItemProvider<Model, MultipleViewHolder> provider, View view, int position) {
                Toast.makeText(HomeActivity.this, provider.get(position).name, Toast.LENGTH_SHORT).show();
            }
        });

        navProvider.setOnProviderClickListener(new OnProviderItemClickListener<AbsItemProvider<Model, MultipleViewHolder>>() {
            @Override
            public void onProviderClick(AbsItemProvider<Model, MultipleViewHolder> provider, View view, int position) {

            }
        });

        homeItemProvider.setOnProviderClickListener(new OnProviderItemClickListener<AbsItemProvider<Model, MultipleViewHolder>>() {
            @Override
            public void onProviderClick(AbsItemProvider<Model, MultipleViewHolder> provider, View view, int position) {
                Toast.makeText(HomeActivity.this, "multipleItem" + position, Toast.LENGTH_SHORT).show();
            }
        });

        singleLineProvider.setOnProviderClickListener(new OnProviderItemClickListener<AbsItemProvider<Model, MultipleViewHolder>>() {
            @Override
            public void onProviderClick(AbsItemProvider<Model, MultipleViewHolder> provider, View view, int position) {
                Toast.makeText(HomeActivity.this, "singleItem" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
