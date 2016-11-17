package com.xjy.demo;

import android.content.Context;

import com.xjy.jymultipleadapter.ItemProvider;
import com.xjy.jymultipleadapter.MultipleViewHolder;

/**
 * User: Tom
 * Date: 2016-11-16
 * Time: 15:33
 * FIXME
 */
public class Item1Provider extends ItemProvider {

    public Item1Provider(Context context) {
        super(context);
    }

    @Override
    public int onInflateLayout() {
        return R.layout.item_1;
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder viewHolder, int position, Object item) {

    }


}