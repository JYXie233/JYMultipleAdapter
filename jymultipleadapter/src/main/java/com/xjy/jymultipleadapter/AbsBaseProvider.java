package com.xjy.jymultipleadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: Tom
 * Date: 2016-11-18
 * Time: 16:27
 * FIXME
 */
public abstract class AbsBaseProvider<M,VH extends MultipleViewHolder>{
    private Context mContext;

    private int mStartNum = 0;

    private int mSpanSize = 1;

    public AbsBaseProvider() {

    }

    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH multipleViewHolder = (VH) new MultipleViewHolder(LayoutInflater.from(getContext()).inflate(onInflateLayout(), parent, false));
        return multipleViewHolder;
    }

    public abstract int onInflateLayout();

    public abstract void onBindViewHolder(VH viewHolder, int position, M item);

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public int getStartNum() {
        return mStartNum;
    }

    public void setStartNum(int startNum) {
        mStartNum = startNum;
    }

    public int getSpanSize() {
        return mSpanSize;
    }

    public void setSpanSize(int spanSize) {
        mSpanSize = spanSize;
    }
}