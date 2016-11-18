package com.xjy.demo.home.provider;

import com.xjy.demo.Model;
import com.xjy.demo.R;
import com.xjy.jymultipleadapter.ItemProvider;
import com.xjy.jymultipleadapter.MultipleViewHolder;

/**
 * User: Tom
 * Date: 2016-11-18
 * Time: 17:33
 * FIXME
 */
public class HomeItemProvider extends ItemProvider<Model>{
    @Override
    public int onInflateLayout() {
        return R.layout.item_home_item;
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder viewHolder, int position, Model item) {
        viewHolder.setText(R.id.textView, item.name);
    }

}