package com.xjy.jymultipleadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Tom
 * Date: 2016-11-18
 * Time: 09:46
 * FIXME
 */
public abstract class AbsItemProvider<M, VH extends MultipleViewHolder> extends AbsBaseProvider<M, VH> implements OnMultipleItemClickListener, OnMultipleItemLongClickListener{

    private List<M> mDataList;



    private OnMultipleItemClickListener mOnMultipleItemClickListener;

    private OnMultipleItemLongClickListener mOnMultipleItemLongClickListener;

    private Map<Integer, OnMultipleItemClickListener> mOnMultipleItemClickListenerMap;

    private Map<Integer, OnMultipleItemLongClickListener> mOnMultipleItemLongClickListenerMap;

    private AbsHeaderFooterProvider mHeaderProvider;
    private AbsHeaderFooterProvider mFooterProvider;

    private Object mHeaderData;

    private Object mFooterData;

    public AbsItemProvider() {
        mDataList = new ArrayList<>();
    }


    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH multipleViewHolder = super.onCreateViewHolder(parent, viewType);
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

    public AbsItemProvider<M, VH> setOnClickViewListener(int viewId, OnMultipleItemClickListener listener) {
        if (mOnMultipleItemClickListenerMap == null){
            mOnMultipleItemClickListenerMap = new HashMap<>();
        }
        mOnMultipleItemClickListenerMap.put(viewId, listener);
        return this;
    }

    public AbsItemProvider<M, VH> setOnLongClickViewListener(int viewId, OnMultipleItemLongClickListener listener) {
        if (mOnMultipleItemLongClickListenerMap == null){
            mOnMultipleItemLongClickListenerMap = new HashMap<>();
        }
        mOnMultipleItemLongClickListenerMap.put(viewId, listener);
        return this;
    }

    public Map<Integer, OnMultipleItemClickListener> getOnMultipleItemClickListenerMap() {
        return mOnMultipleItemClickListenerMap;
    }

    public Map<Integer, OnMultipleItemLongClickListener> getOnMultipleItemLongClickListenerMap() {
        return mOnMultipleItemLongClickListenerMap;
    }

    public AbsItemProvider add(M itemType) {
        mDataList.add(itemType);
        return this;
    }

    public void addAll(List<M> list) {
        mDataList.addAll(list);
    }

    public void clear() {
        mDataList.clear();
    }

    public void remove(int location) {
        mDataList.remove(location);
    }

    public void remove(M itemType) {
        mDataList.remove(itemType);
    }

    public int size() {
        return mDataList.size();
    }

    public M get(int position) {
        if (position == -1)
            return null;
        return mDataList.get(position);
    }

    public List<M> getDataList(){
        return mDataList;
    }


    public <H> void registerHeaderProvider(H h, AbsHeaderFooterProvider<H> headerProvider){
        mHeaderData = h;
        mHeaderProvider = headerProvider;
    }

    public <H> void registerFooterProvider(H h, AbsHeaderFooterProvider<H> footerProvider){
        mFooterData = h;
        mFooterProvider = footerProvider;
    }

    public AbsHeaderFooterProvider getHeaderProvider() {
        return mHeaderProvider;
    }

    public AbsHeaderFooterProvider getFooterProvider() {
        return mFooterProvider;
    }

    public Object getHeaderData() {
        return mHeaderData;
    }

    public Object getFooterData() {
        return mFooterData;
    }
}