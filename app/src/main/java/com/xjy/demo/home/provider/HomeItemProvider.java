package com.xjy.demo.home.provider;

import com.xjy.demo.Model;
import com.xjy.demo.R;
import com.xjy.widget.ItemProvider;
import com.xjy.widget.ItemProviderActionHelper;
import com.xjy.widget.MultipleViewHolder;

/**
 * User: Tom
 * Date: 2016-11-18
 * Time: 17:33
 * FIXME
 */
public class HomeItemProvider extends ItemProvider<Model> implements ItemProviderActionHelper{
    @Override
    public int onInflateLayout() {
        return R.layout.item_home_item;
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder viewHolder, int position, Model item) {
        viewHolder.setText(R.id.textView, item.name);
    }

    @Override
    public boolean isItemCanSwipe(int position) {
        return false;
    }

    @Override
    public boolean isItemCanMove(int position) {
        return true;
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