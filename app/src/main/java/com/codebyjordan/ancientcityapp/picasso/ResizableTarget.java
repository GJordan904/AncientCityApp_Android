package com.codebyjordan.ancientcityapp.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.codebyjordan.ancientcityapp.custviews.DynamicHeightImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ResizableTarget implements Target {

    private DynamicHeightImageView mView;

    public ResizableTarget(DynamicHeightImageView view) {
            mView = view;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
        Log.v("RecyclerAdapter", "Aspect Ratio: " + ratio);
        mView.setAspectRatio(ratio);
        mView.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
