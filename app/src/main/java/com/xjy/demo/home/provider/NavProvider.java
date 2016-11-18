package com.xjy.demo.home.provider;

import com.xjy.demo.Model;
import com.xjy.demo.R;
import com.xjy.jymultipleadapter.ItemProvider;
import com.xjy.jymultipleadapter.MultipleViewHolder;

/**
 * User: Tom
 * Date: 2016-11-18
 * Time: 17:32
 * FIXME
 */
public class NavProvider extends ItemProvider<Model>{
    @Override
    public int onInflateLayout() {
        return R.layout.item_home_nav;
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder viewHolder, int position, Model item) {
        viewHolder.setText(R.id.textView, item.name);
    }
}