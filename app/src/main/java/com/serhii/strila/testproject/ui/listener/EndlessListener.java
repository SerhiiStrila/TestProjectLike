package com.serhii.strila.testproject.ui.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessListener extends RecyclerView.OnScrollListener {

    private int mPreviousTotal; // The total number of items in the dataset after the last load

    // True if we are still waiting for the last set of data to load.
    private boolean mLoading = true;

    private int mVisibleThreshold = 5;

    // The minimum amount of items to have below your current scroll position before loading more.
    private int mCurrentPage = 0;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public void reLoad() {
        mPreviousTotal = 0;
        mLoading = false;
        mCurrentPage = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy < 0) {
            return;
        }
        int totalItemCount = mLinearLayoutManager.getItemCount();
        if (mLoading && totalItemCount > mPreviousTotal) {
            mLoading = false;
            mPreviousTotal = totalItemCount;
        }
        if (!mLoading && mLinearLayoutManager.findLastCompletelyVisibleItemPosition()
                >= totalItemCount - mVisibleThreshold) {
            mCurrentPage++;
            onLoadMore(mCurrentPage);
            mLoading = true;
        }

    }

    public abstract void onLoadMore(int currentPage);
}