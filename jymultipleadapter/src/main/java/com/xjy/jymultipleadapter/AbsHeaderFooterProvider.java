package com.xjy.jymultipleadapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * User: Tom
 * Date: 2016-11-18
 * Time: 16:04
 * FIXME
 */
public abstract class AbsHeaderFooterProvider<M> extends AbsItemProvider<M, MultipleViewHolder>{
    private SparseIntArray mPositionSection;

    private HashMap<Integer, M> mHeaderData;
    private HashMap<Integer, M> mFooterData;

    public AbsHeaderFooterProvider() {

        mPositionSection = new SparseIntArray();
        mHeaderData = new HashMap<>();
        mFooterData = new HashMap<>();
    }

    @Override
    public MultipleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MultipleViewHolder holder = super.onCreateViewHolder(parent, viewType);
        holder.setOnMultipleItemClickListener(new OnMultipleItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (getOnMultipleItemClickListener() != null)
                    getOnMultipleItemClickListener().onClick(view, getSection(position));
            }
        });
        holder.setOnMultipleItemLongClickListener(new OnMultipleItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                if (getOnMultipleItemLongClickListener() != null)
                    return getOnMultipleItemLongClickListener().onItemLongClick(view, getSection(position));
                return false;
            }
        });

        if (getOnMultipleItemClickListenerMap() != null){
            for (int id : getOnMultipleItemClickListenerMap().keySet()){
                holder.setClickListener(id, this);
            }
        }

        if (getOnMultipleItemClickListenerMap() != null){
            for (int id : getOnMultipleItemClickListenerMap().keySet()){
                holder.setLongClickListener(id, this);
            }
        }
        return holder;
    }

    @Override
    public void onClick(View view, int position) {
        if (getOnMultipleItemClickListenerMap() != null) {
            getOnMultipleItemClickListenerMap().get(view.getId()).onClick(view, getSection(position));
        }
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        if (getOnMultipleItemLongClickListenerMap() != null) {
            return getOnMultipleItemLongClickListenerMap().get(view.getId()).onItemLongClick(view, getSection(position));
        }
        return false;
    }

    public void put(int position, int section){
        mPositionSection.put(position, section);
    }

    public void putHeader(M m, int section){
        mHeaderData.put(section, m);
    }

    public void putFooter(M m, int section){
        mFooterData.put(section, m);
    }

    public int getSection(int position){
        return mPositionSection.get(position);
    }

    public M getHeaderData(int section){
        return mHeaderData.get(section);
    }

    public M getFooterData(int section){
        return mFooterData.get(section);
    }
}