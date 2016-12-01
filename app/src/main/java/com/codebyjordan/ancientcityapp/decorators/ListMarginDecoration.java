package com.codebyjordan.ancientcityapp.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ListMarginDecoration extends RecyclerView.ItemDecoration {

    private final int mMargin;

    public ListMarginDecoration(int margin) {
        mMargin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mMargin;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = mMargin;
    }
}
