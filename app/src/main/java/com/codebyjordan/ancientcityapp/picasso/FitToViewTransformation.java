package com.codebyjordan.ancientcityapp.picasso;

import android.graphics.Bitmap;
import android.util.Log;

import com.codebyjordan.ancientcityapp.custviews.DynamicHeightImageView;
import com.squareup.picasso.Transformation;


public class FitToViewTransformation implements Transformation {

    private DynamicHeightImageView mView;

    public FitToViewTransformation(DynamicHeightImageView view) {
        mView = view;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();

        while(mView.getWidth() == 0) {

        }

        int targetWidth = mView.getWidth();
        Log.v("FitToView:", "Target width: " + targetWidth);

        int targetHeight = (int) (targetWidth * aspectRatio);


        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
        if (result != source) {
            // Same bitmap is returned if sizes are the same
            source.recycle();
        }

        return result;
    }

    @Override
    public String key() {
        return "transformation" + " desiredWidth";
    }
}