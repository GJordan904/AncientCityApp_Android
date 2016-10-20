package com.codebyjordan.ancientcityapp;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {

    private final int mMargin;

    public MarginItemDecoration(int margin) {
        mMargin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mMargin;
        outRect.right = mMargin;
        outRect.bottom = mMargin;
        // Add top margin only for the first 2 items to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1)
            outRect.top = mMargin;
    }
}
