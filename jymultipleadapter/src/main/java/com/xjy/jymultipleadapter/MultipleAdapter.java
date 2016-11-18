package com.xjy.jymultipleadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Tom
 * Date: 2016-11-16
 * Time: 11:15
 * FIXME
 */
public class MultipleAdapter extends RecyclerView.Adapter<MultipleViewHolder> implements JYItemTouchHelperAdapter {

    private Map<Integer, AbsItemProvider> mViewTypeForProviderMap;

    private ArrayList<Integer> mProviderOrderArray;

    private ArrayList<Integer> mHeaderPositions;
    private ArrayList<Integer> mFooterPositions;

    private SparseIntArray mPositionViewTypes;

    private int mSpanSize = 1;


    private Context mContext;

    public MultipleAdapter(Context context) {
        this.mContext = context;
        mViewTypeForProviderMap = new HashMap<>();
        mProviderOrderArray = new ArrayList<>();
        mPositionViewTypes = new SparseIntArray();
        mHeaderPositions = new ArrayList<>();
        mFooterPositions = new ArrayList<>();
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                calculationCount();
            }
        });
    }


    @Override
    public MultipleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AbsBaseProvider itemCreator = mViewTypeForProviderMap.get(viewType);
        Log.d("viewType:", viewType + "");
        return itemCreator.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder holder, int position) {
        int type = mPositionViewTypes.get(position);
        AbsItemProvider itemCreator = mViewTypeForProviderMap.get(type);
        int startNum = itemCreator.getStartNum();
        if (mHeaderPositions.contains(position)) {
            AbsHeaderFooterProvider headerFooterProvider = (AbsHeaderFooterProvider) itemCreator;
            int section = headerFooterProvider.getSection(position);
            headerFooterProvider.onBindViewHolder(holder, section, headerFooterProvider.getHeaderData(section));
        } else if (mFooterPositions.contains(position)) {
            AbsHeaderFooterProvider headerFooterProvider = (AbsHeaderFooterProvider) itemCreator;
            int section = headerFooterProvider.getSection(position);
            headerFooterProvider.onBindViewHolder(holder, section, headerFooterProvider.getFooterData(section));
        } else {
            itemCreator.onBindViewHolder(holder, position - startNum, itemCreator.get(position - startNum));
        }

    }

    @Override
    public int getItemCount() {
        int count = calculationCount();
        Log.d("count", "count:" + count);
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (mPositionViewTypes.size() <= position) {
            calculationCount();
        }
        Log.d("itemViewType:", "viewType:" + mPositionViewTypes.get(position) + " position:" + position);
        return mPositionViewTypes.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        ItemTouchHelper.Callback callback = new JYItemTouchHelperCallback(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            mSpanSize = gridLayoutManager.getSpanCount();
            gridLayoutManager.setSpanSizeLookup(mSpanSizeLookup);
        }
    }

    private GridLayoutManager.SpanSizeLookup mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            calculationCount();
            int count = 1;
            Integer type = mPositionViewTypes.get(position);
            AbsBaseProvider itemProvider = mViewTypeForProviderMap.get(type);
            if (itemProvider == null) {
                count = 1;
            } else {
                count = mSpanSize / itemProvider.getSpanSize();
            }

            return count;

        }
    };

    private synchronized int calculationCount() {
        int count = 0;
        int startNum = 0;
        for (int i = 0; i < mProviderOrderArray.size(); i++) {
            Integer type = mProviderOrderArray.get(i);
            AbsItemProvider itemProvider = mViewTypeForProviderMap.get(type);
            if (itemProvider.getHeaderProvider() != null) {
                int headerType = itemProvider.getHeaderProvider().hashCode();
                Log.d("calculationCount", "Header hashCode:" + headerType + " position:" + startNum);
                mPositionViewTypes.put(startNum, headerType);
                mHeaderPositions.add(startNum);
                itemProvider.getHeaderProvider().put(startNum, i);
                startNum += 1;
                count++;
            }
            int num = itemProvider.size();

            for (int j = 0; j < num; j++) {
                Log.d("calculationCount", "Item hashCode:" + type + " position:" + (startNum + j));
                mPositionViewTypes.put(j + startNum, type);
            }

            itemProvider.setStartNum(startNum);
            startNum += num;

            count += num;
            if (itemProvider.getFooterProvider() != null) {
                int footerType = itemProvider.getFooterProvider().hashCode();
                Log.d("calculationCount", "Footer hashCode:" + footerType + " position:" + startNum);
                mPositionViewTypes.put(startNum, footerType);
                mFooterPositions.add(startNum);
                itemProvider.getFooterProvider().put(startNum, i);
                startNum += 1;
                count++;
            }
        }
        return count;
    }

    public <M> AbsItemProvider<M, MultipleViewHolder> registerProvider(AbsItemProvider<M, MultipleViewHolder> itemProvider) {
        itemProvider.setContext(mContext);
        Log.d("Register", "layoutId:" + itemProvider.onInflateLayout() + " hashCode:" + itemProvider.hashCode());
        int type = itemProvider.hashCode();
        mViewTypeForProviderMap.put(type, itemProvider);
        if (itemProvider.getHeaderProvider() != null) {
            itemProvider.getHeaderProvider().setContext(mContext);
            itemProvider.getHeaderProvider().putHeader(itemProvider.getHeaderData(), mProviderOrderArray.size());
            mViewTypeForProviderMap.put(itemProvider.getHeaderProvider().hashCode(), itemProvider.getHeaderProvider());
        }
        if (itemProvider.getFooterProvider() != null) {
            itemProvider.getFooterProvider().setContext(mContext);
            itemProvider.getFooterProvider().putFooter(itemProvider.getFooterData(), mProviderOrderArray.size());
            mViewTypeForProviderMap.put(itemProvider.getFooterProvider().hashCode(), itemProvider.getFooterProvider());
        }
        mProviderOrderArray.add(type);
        Log.d("register", type + "");
        return itemProvider;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        int oldType = mPositionViewTypes.get(fromPosition);
        int newType = mPositionViewTypes.get(toPosition);

        AbsItemProvider oldItemProvider = mViewTypeForProviderMap.get(oldType);
        AbsItemProvider newItemProvider = mViewTypeForProviderMap.get(newType);
        if (oldItemProvider instanceof ItemProviderActionHelper) {
            boolean handle = ((ItemProviderActionHelper) oldItemProvider).onItemMove(fromPosition - oldItemProvider.getStartNum(), toPosition - oldItemProvider.getStartNum());
            if (!handle) {
                if (oldItemProvider.equals(newItemProvider)) {
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
        int type = mPositionViewTypes.get(position);
        AbsItemProvider itemCreator = mViewTypeForProviderMap.get(type);
        if (itemCreator instanceof ItemProviderActionHelper) {
            boolean handle = ((ItemProviderActionHelper) itemCreator).onItemSwipe(position - itemCreator.getStartNum());
            if (!handle) {
                itemCreator.remove(position - itemCreator.getStartNum());
                notifyItemRangeRemoved(position, 1);
            }
        }
    }

    @Override
    public boolean canSwipe(int position) {
        int type = mPositionViewTypes.get(position);
        AbsItemProvider itemCreator = mViewTypeForProviderMap.get(type);
        if (itemCreator instanceof ItemProviderActionHelper) {
            return ((ItemProviderActionHelper) itemCreator).isItemCanSwipe(position - itemCreator.getStartNum());
        }
        return false;
    }

    @Override
    public boolean canMove(int position) {
        int type = mPositionViewTypes.get(position);
        AbsItemProvider itemCreator = mViewTypeForProviderMap.get(type);
        if (itemCreator instanceof ItemProviderActionHelper) {
            return ((ItemProviderActionHelper) itemCreator).isItemCanMove(position - itemCreator.getStartNum());
        }
        return false;
    }

}