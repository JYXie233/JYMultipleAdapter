package com.xjy.jymultipleadapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User: Tom
 * Date: 2016-11-16
 * Time: 11:15
 * FIXME
 */
public class MultipleAdapter extends RecyclerView.Adapter<MultipleViewHolder> implements JYItemTouchHelperAdapter{

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
        for (ItemProvider itemProvider : mItemTypeItemProviderMap.values()) {
            count += itemProvider.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return getMultipleItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        ItemTouchHelper.Callback callback = new JYItemTouchHelperCallback(this);
        ItemTouchHelper itemTouchHelpler = new ItemTouchHelper(callback);
        itemTouchHelpler.attachToRecyclerView(recyclerView);
    }

    private int getMultipleItemViewType(int position) {
        int startNum = 0;

        for (int i = 0; i < mProviderOrderArray.size(); i++) {

            Integer type = mProviderOrderArray.get(i);
            ItemProvider itemProvider = mItemTypeItemProviderMap.get(type);
            int count = itemProvider.size();

            itemProvider.setStartNum(startNum);
            if (position < startNum + count && position >= startNum) {
                mViewTypes.put(position, type);
                return type;
            } else {
                startNum += count;
            }

        }
        return 0;
    }

    public <M> void registerCreator(Class<? extends M> typeClass, ItemProvider<M> itemCreator) {

        int type = itemCreator.getClass().hashCode() + itemCreator.generateSubType();

        mItemTypeItemProviderMap.put(type, itemCreator);
        mProviderOrderArray.add(type);
        Log.d("register type:", type + "");
    }

    public void registerCreator(ItemProvider itemCreator) {
        registerCreator(itemCreator.getClass(), itemCreator);
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        int oldType = mViewTypes.get(fromPosition);
        int newType = mViewTypes.get(toPosition);
        ItemProvider oldItemProvider = mItemTypeItemProviderMap.get(oldType);
        ItemProvider newItemProvider = mItemTypeItemProviderMap.get(newType);
        if (oldItemProvider instanceof ItemProviderActionHelper){
            boolean handle = ((ItemProviderActionHelper)oldItemProvider).onItemMove(fromPosition - oldItemProvider.getStartNum(), toPosition - oldItemProvider.getStartNum());
            if (!handle){
                if (oldItemProvider.equals(newItemProvider)){
                    int oldPosition = fromPosition - oldItemProvider.getStartNum();
                    int newPosition = toPosition - oldItemProvider.getStartNum();
                    Collections.swap(oldItemProvider.getDataList(), oldPosition, newPosition);
                    notifyItemMoved(fromPosition, toPosition);
                }
            }
        }
    }

    @Override
    public void onItemDismiss(int position) {
        int type = mViewTypes.get(position);
        ItemProvider itemCreator = mItemTypeItemProviderMap.get(type);
        if (itemCreator instanceof ItemProviderActionHelper){
            boolean handle = ((ItemProviderActionHelper)itemCreator).onItemSwipe(position - itemCreator.getStartNum());
            if (!handle){
                itemCreator.remove(position - itemCreator.getStartNum());
                notifyItemRangeRemoved(position, 1);
            }
        }
    }

    @Override
    public boolean canSwipe(int position) {
        int type = mViewTypes.get(position);
        ItemProvider itemCreator = mItemTypeItemProviderMap.get(type);
        if (itemCreator instanceof ItemProviderActionHelper){
            return ((ItemProviderActionHelper)itemCreator).isItemCanSwipe(position - itemCreator.getStartNum());
        }
        return false;
    }

    @Override
    public boolean canMove(int position) {
        int type = mViewTypes.get(position);
        ItemProvider itemCreator = mItemTypeItemProviderMap.get(type);
        if (itemCreator instanceof ItemProviderActionHelper){
            return ((ItemProviderActionHelper)itemCreator).isItemCanMove(position - itemCreator.getStartNum());
        }
        return false;
    }
}