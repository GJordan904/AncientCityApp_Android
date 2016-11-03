package com.codebyjordan.ancientcityapp.custviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class DynamicHeightImageView extends ImageView {

    private double mAspectRatio;

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAspectRatio(double ratio) {
        Log.v("setAspectRatio", "aspect ratio set");
        mAspectRatio = ratio;
        requestLayout();
    }

    public void setAspectRatio(double ratio, boolean waiting) {
        Log.v("setAspectRatio in Wait", "aspect ratio set");
        mAspectRatio = ratio;

    }

    public double getAspectRatio() {
        return mAspectRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAspectRatio > 0.0) {
            Log.v("onMeasure", "Setting imageviews size");
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mAspectRatio);
            setMeasuredDimension(width, height);
        }
        else {
            Log.v("onMeasure", "Aspect ratio not set");
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
