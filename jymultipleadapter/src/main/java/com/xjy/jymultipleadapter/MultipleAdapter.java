package com.xjy.jymultipleadapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Tom
 * Date: 2016-11-16
 * Time: 11:15
 * FIXME
 */
public class MultipleAdapter extends RecyclerView.Adapter<MultipleViewHolder>{

    private Map<Integer, ItemProvider> mItemTypeItemProviderMap;

    private ArrayList<Integer> mProviderOrderArray;

    private SparseIntArray mViewTypes;

    public MultipleAdapter() {
        mItemTypeItemProviderMap = new HashMap<>();
        mProviderOrderArray = new ArrayList<>();
        mViewTypes = new SparseIntArray();
    }

    @Override
    public MultipleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemProvider itemCreator = mItemTypeItemProviderMap.get(viewType);
        Log.d("viewType:", viewType + "");
        return itemCreator.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder holder, int position) {
        int type = mViewTypes.get(position);
        ItemProvider itemCreator = mItemTypeItemProviderMap.get(type);
        int startNum = itemCreator.getStartNum();
        itemCreator.onBindViewHolder(holder, position - startNum, itemCreator.get(position - startNum));
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (ItemProvider itemProvider : mItemTypeItemProviderMap.values()){
            count += itemProvider.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return getMultipleItemViewType(position);
    }

    private int getMultipleItemViewType(int position){
        int startNum = 0;

        for (int i = 0 ; i < mProviderOrderArray.size(); i ++){

            Integer type = mProviderOrderArray.get(i);
            ItemProvider itemProvider = mItemTypeItemProviderMap.get(type);
            int count = itemProvider.size();

            itemProvider.setStartNum(startNum);
            if (position < startNum + count && position >= startNum){
                Log.d("itemProviderSize:","i:" +i+ " position: " + position + " type :" + type + " count:"+ count + " startNum:" + startNum + " class:" + itemProvider.getClass());
                mViewTypes.put(position, type);
                return type;
            }else {
                startNum += count;
            }

        }
        return 0;
    }

    public void registerCreator(Class<? extends ItemType> typeClass, ItemProvider itemCreator){
        mItemTypeItemProviderMap.put(typeClass.hashCode(), itemCreator);
        mProviderOrderArray.add(typeClass.hashCode());
        Log.d("register type:", typeClass.hashCode() + "");
    }

    public void registerCreator(ItemProvider itemCreator){
        registerCreator((Class<? extends ItemType>) itemCreator.getClass(), itemCreator);
    }


}