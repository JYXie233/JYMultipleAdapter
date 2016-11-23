package com.xjy.demo.home.provider;

import com.xjy.demo.R;
import com.xjy.widget.ItemProvider;
import com.xjy.widget.MultipleViewHolder;

/**
 * User: Tom
 * Date: 2016-11-18
 * Time: 17:31
 * FIXME
 */
public class BannerProvider extends ItemProvider<String> {
    @Override
    public int onInflateLayout() {
        return R.layout.item_home_banner;
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder viewHolder, int position, String item) {
        viewHolder.setText(R.id.textView, item);
    }
}