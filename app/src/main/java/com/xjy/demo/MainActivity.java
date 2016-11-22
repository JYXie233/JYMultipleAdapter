package com.xjy.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xjy.demo.contact.ContactActivity;
import com.xjy.demo.home.HomeActivity;
import com.xjy.jymultipleadapter.AbsItemProvider;
import com.xjy.jymultipleadapter.ItemProvider;
import com.xjy.jymultipleadapter.MultipleAdapter;
import com.xjy.jymultipleadapter.MultipleViewHolder;
import com.xjy.jymultipleadapter.OnProviderItemClickListener;

public class MainActivity extends AppCompatActivity implements OnProviderItemClickListener<AbsItemProvider<Model, MultipleViewHolder>> {

    private MultipleAdapter mMultipleAdapter;

    MainProvider mMainProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mMultipleAdapter = new MultipleAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMainProvider = new MainProvider();
        mMainProvider.add(new Model("常见首页效果", HomeActivity.class));
        mMainProvider.add(new Model("联系人效果", ContactActivity.class));

        mMultipleAdapter.registerProvider(mMainProvider);

        recyclerView.setAdapter(mMultipleAdapter);


        mMainProvider.setOnProviderClickListener(this);

    }

    @Override
    public void onProviderClick(AbsItemProvider<Model, MultipleViewHolder> provider, View view, int position) {
        Class clazz = mMainProvider.get(position).clazz;
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
