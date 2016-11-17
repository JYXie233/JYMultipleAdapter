package com.xjy.demo;

import android.content.Context;

import com.xjy.jymultipleadapter.ItemProvider;
import com.xjy.jymultipleadapter.ItemProviderActionHelper;
import com.xjy.jymultipleadapter.MultipleViewHolder;

/**
 * User: Tom
 * Date: 2016-11-17
 * Time: 14:56
 * FIXME
 */
public class Item3Provider extends ItemProvider<Item3> implements ItemProviderActionHelper{

    public Item3Provider(Context context) {
        super(context);
    }

    @Override
    public int onInflateLayout() {
        return R.layout.item_3;
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder viewHolder, int position, Item3 item) {
        viewHolder.setText(R.id.textView, position + "item" + item.order);
    }

    @Override
    public int generateSubType() {
        return 0;
    }

    @Override
    public boolean isItemCanSwipe(int position) {
        return true;
    }

    @Override
    public boolean isItemCanMove(int position) {
        return false;
    }

    @Override
    public boolean onItemSwipe(int position) {
        return false;
    }

    @Override
    public boolean onItemMove(int oldPosition, int newPosition) {
        return false;
    }
}