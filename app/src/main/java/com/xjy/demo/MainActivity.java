package com.xjy.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xjy.demo.home.HomeActivity;
import com.xjy.jymultipleadapter.MultipleAdapter;
import com.xjy.jymultipleadapter.OnMultipleItemClickListener;

public class MainActivity extends AppCompatActivity implements OnMultipleItemClickListener{

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

        mMultipleAdapter.registerProvider(mMainProvider);

        recyclerView.setAdapter(mMultipleAdapter);

        mMainProvider.setOnMultipleItemClickListener(this);

    }

    @Override
    public void onClick(View view, int position) {
        Class clazz = mMainProvider.get(position).clazz;
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
