package com.codebyjordan.ancientcityapp.picasso;

import android.graphics.Bitmap;
import android.view.View;

import android.widget.ImageView;
import com.squareup.picasso.Transformation;


public class FitToViewTransformation implements Transformation {
    private ImageView view;

    public FitToViewTransformation(ImageView view) {
        this.view = view;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = view.getWidth();

        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
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