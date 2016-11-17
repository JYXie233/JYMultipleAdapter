package com.xjy.demo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xjy.jymultipleadapter.MultipleAdapter;
import com.xjy.jymultipleadapter.OnMultipleItemClickListener;

public class MainActivity extends AppCompatActivity {

    private MultipleAdapter mMultipleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mMultipleAdapter = new MultipleAdapter();

        final Item1Provider item1Creator = new Item1Provider(this);

        mMultipleAdapter.registerCreator(item1Creator);

        final Item2Provider item2Creator = new Item2Provider(this);
        mMultipleAdapter.registerCreator(Item2.class, item2Creator);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mMultipleAdapter.notifyDataSetChanged();

        item1Creator.setOnMultipleItemClickListener(new OnMultipleItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "item1" + position, Toast.LENGTH_SHORT).show();
            }
        });

        item2Creator.setOnMultipleItemClickListener(new OnMultipleItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "item2" + position, Toast.LENGTH_SHORT).show();
            }
        });

        item2Creator.setOnClickViewListener(R.id.textView, new OnMultipleItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "textView" + position, Toast.LENGTH_SHORT).show();
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                item1Creator.add(item1Creator);

                item2Creator.add(new Item2());

                item2Creator.add(new Item2());

                item2Creator.add(new Item2());

                item2Creator.add(new Item2());

                recyclerView.setAdapter(mMultipleAdapter);
            }
        }, 1000);
    }
}
