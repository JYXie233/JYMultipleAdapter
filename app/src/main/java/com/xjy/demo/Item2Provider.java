package com.xjy.demo;

import android.content.Context;

import com.xjy.jymultipleadapter.ItemProvider;
import com.xjy.jymultipleadapter.ItemType;
import com.xjy.jymultipleadapter.MultipleViewHolder;

/**
 * User: Tom
 * Date: 2016-11-16
 * Time: 15:33
 * FIXME
 */
public class Item2Provider extends ItemProvider<Item2> implements ItemType {

    public Item2Provider(Context context) {
        super(context);
    }

    @Override
    public int onInflateLayout() {
        return R.layout.item_2;
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder viewHolder, int position, Item2 item) {

    }


    @Override
    public boolean canSwipe() {
        return false;
    }

    @Override
    public boolean canMove() {
        return false;
    }
}