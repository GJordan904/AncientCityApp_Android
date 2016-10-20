package com.codebyjordan.ancientcityapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import com.codebyjordan.ancientcityapp.DynamicHeightImageView;
import com.codebyjordan.ancientcityapp.FitToViewTransformation;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.yelp.models.Business;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesView> {

    private Context mContext;
    private List<Business> mPlaces;
    private FitToViewTransformation mFitToView;

    public PlacesAdapter(Context context, List<Business> places) {
        mContext = context;
        mPlaces = places;
    }

    @Override
    public PlacesView onCreateViewHolder(ViewGroup parent, int i) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_places, parent, false);

        mFitToView = new FitToViewTransformation(view);

        return new PlacesView(view);
    }

    @Override
    public void onBindViewHolder(PlacesView placesView, int i) {

        // Setup details variables
        Business place = mPlaces.get(i);
        String name = place.getName();
        String phone = place.getPhone();
        String photo = place.getImage_url();

        Picasso.with(mContext)
                .load(photo)
                .transform(mFitToView)
                .into(placesView.placeImage);

        placesView.nameText.setText(name);
        placesView.phoneText.setText(phone);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public class PlacesView extends RecyclerView.ViewHolder implements Target {
        DynamicHeightImageView placeImage;
        TextView nameText;
        TextView phoneText;

        public PlacesView(View itemView) {
            super(itemView);
            placeImage = (DynamicHeightImageView) itemView.findViewById(R.id.placeImage);
            nameText = (TextView) itemView.findViewById(R.id.nameText);
            phoneText = (TextView) itemView.findViewById(R.id.phoneText);
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
            placeImage.setHeightRatio(ratio);
            placeImage.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }
}
