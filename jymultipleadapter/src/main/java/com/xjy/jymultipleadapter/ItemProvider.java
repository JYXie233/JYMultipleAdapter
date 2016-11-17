package com.xjy.jymultipleadapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Tom
 * Date: 2016-11-16
 * Time: 11:23
 * FIXME
 */
public abstract class ItemProvider<T extends ItemType> implements OnMultipleItemClickListener, OnMultipleItemLongClickListener{

    private Context mContext;

    private List<T> mDataList;

    private int mStartNum = 0;

    private OnMultipleItemClickListener mOnMultipleItemClickListener;

    private OnMultipleItemLongClickListener mOnMultipleItemLongClickListener;

    private Map<Integer, OnMultipleItemClickListener> mOnMultipleItemClickListenerMap;

    private Map<Integer, OnMultipleItemLongClickListener> mOnMultipleItemLongClickListenerMap;

    public ItemProvider(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();

    }

    public MultipleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MultipleViewHolder multipleViewHolder = new MultipleViewHolder(LayoutInflater.from(mContext).inflate(onInflateLayout(), parent, false));
        multipleViewHolder.setOnMultipleItemClickListener(new OnMultipleItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (mOnMultipleItemClickListener != null)
                    mOnMultipleItemClickListener.onClick(view, position - getStartNum());
            }
        });
        multipleViewHolder.setOnMultipleItemLongClickListener(new OnMultipleItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                if (mOnMultipleItemLongClickListener != null)
                    return mOnMultipleItemLongClickListener.onItemLongClick(view, position - getStartNum());
                return false;
            }
        });

        if (mOnMultipleItemClickListenerMap != null){
            for (int id : mOnMultipleItemClickListenerMap.keySet()){
                multipleViewHolder.setClickListener(id, this);
            }
        }

        if (mOnMultipleItemLongClickListenerMap != null){
            for (int id : mOnMultipleItemLongClickListenerMap.keySet()){
                multipleViewHolder.setLongClickListener(id, this);
            }
        }


        return multipleViewHolder;
    }

    @Override
    public void onClick(View view, int position) {
        if (mOnMultipleItemClickListenerMap != null) {
            mOnMultipleItemClickListenerMap.get(view.getId()).onClick(view, position - getStartNum());
        }
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        if (mOnMultipleItemLongClickListenerMap != null) {
            return mOnMultipleItemLongClickListenerMap.get(view.getId()).onItemLongClick(view, position - getStartNum());
        }
        return false;
    }

    public abstract @LayoutRes int onInflateLayout();

    public abstract void onBindViewHolder(MultipleViewHolder viewHolder, int position, T item);

    public OnMultipleItemClickListener getOnMultipleItemClickListener() {
        return mOnMultipleItemClickListener;
    }

    public void setOnMultipleItemClickListener(OnMultipleItemClickListener onMultipleItemClickListener) {
        mOnMultipleItemClickListener = onMultipleItemClickListener;
    }

    public OnMultipleItemLongClickListener getOnMultipleItemLongClickListener() {
        return mOnMultipleItemLongClickListener;
    }

    public void setOnMultipleItemLongClickListener(OnMultipleItemLongClickListener onMultipleItemLongClickListener) {
        mOnMultipleItemLongClickListener = onMultipleItemLongClickListener;
    }

    public ItemProvider<T> setOnClickViewListener(@IdRes int viewId, OnMultipleItemClickListener listener) {
        if (mOnMultipleItemClickListenerMap == null){
            mOnMultipleItemClickListenerMap = new HashMap<>();
        }
        mOnMultipleItemClickListenerMap.put(viewId, listener);
        return this;
    }

    public ItemProvider<T> setOnLongClickViewListener(@IdRes int viewId, OnMultipleItemLongClickListener listener) {
        if (mOnMultipleItemLongClickListenerMap == null){
            mOnMultipleItemLongClickListenerMap = new HashMap<>();
        }
        mOnMultipleItemLongClickListenerMap.put(viewId, listener);
        return this;
    }


    public void add(T itemType) {
        mDataList.add(itemType);
    }

    public void addAll(List<T> list) {
        mDataList.addAll(list);
    }

    public void clear() {
        mDataList.clear();
    }

    public void remove(int location) {
        mDataList.remove(location);
    }

    public void remove(T itemType) {
        mDataList.remove(itemType);
    }

    public int size() {
        return mDataList.size();
    }

    public T get(int position) {
        if (position == -1)
            return null;
        return mDataList.get(position);
    }

    public int getStartNum() {
        return mStartNum;
    }

    public void setStartNum(int startNum) {
        mStartNum = startNum;
    }

}