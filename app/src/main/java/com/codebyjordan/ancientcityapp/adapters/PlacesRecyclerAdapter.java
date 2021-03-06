package com.codebyjordan.ancientcityapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import com.codebyjordan.ancientcityapp.custviews.DynamicHeightImageView;
import com.codebyjordan.ancientcityapp.picasso.FitToViewTransformation;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.picasso.ResizableTarget;
import com.codebyjordan.ancientcityapp.ui.MapActivity;
import com.codebyjordan.ancientcityapp.ui.PlaceDetailActivity;
import com.codebyjordan.ancientcityapp.yelp.models.Business;
import com.codebyjordan.ancientcityapp.yelp.models.BusinessLocationCoords;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class PlacesRecyclerAdapter extends RecyclerView.Adapter<PlacesRecyclerAdapter.PlacesView>{

    private Context mContext;
    private List<Business> mPlaces;


    public PlacesRecyclerAdapter(Context context, List<Business> places) {
        mContext = context;
        mPlaces = places;
    }

    //
    // View Holder
    //
    public class PlacesView extends RecyclerView.ViewHolder {
        DynamicHeightImageView placeImage;
        ImageView menuDots;
        TextView nameText;
        TableLayout tableLayout;
        TextView phoneText;
        TextView hoursText;
        TextView addressText;

        public PlacesView(View itemView) {
            super(itemView);
            placeImage = (DynamicHeightImageView) itemView.findViewById(R.id.placeImage);
            menuDots = (ImageView) itemView.findViewById(R.id.menuDots);
            nameText = (TextView) itemView.findViewById(R.id.nameText);
            tableLayout = (TableLayout) itemView.findViewById(R.id.tableLayout);
            phoneText = (TextView) itemView.findViewById(R.id.phoneText);
            hoursText = (TextView) itemView.findViewById(R.id.hoursText);
            addressText = (TextView) itemView.findViewById(R.id.addressText);
        }
    }

    //
    // Recycler Adapter Overrides
    //
    @Override
    public PlacesView onCreateViewHolder(ViewGroup parent, int i) {
        // Check whether on phone or tablet and if landscape or portrait and load correct view
        View view;
        boolean isPhone = mContext.getResources().getBoolean(R.bool.is_phone);
        boolean isLandscape = mContext.getResources().getBoolean(R.bool.is_landscape);
        if(isPhone && !isLandscape) {
            view = LayoutInflater.from(mContext).inflate(R.layout.cardview_places_phone, parent, false);
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.cardview_places, parent, false);
        }

        return new PlacesView(view);
    }

    @Override
    public void onBindViewHolder(final PlacesView placesView, final int i) {

        // Setup details variables
        final Business place = mPlaces.get(i);
        String name = place.getName();
        final String businessId = place.getId();
        final String phone = place.formattedPhone();
        String address = place.getLocation().getAddress()[0];
        final BusinessLocationCoords coords = place.getLocation().getCoordinate();
        String open;
        final String photo = place.getImage_url();
        if(place.is_closed()){
            open = "Closed";
        }else{
            open = "Open";
        }

        // Set Text Views
        placesView.nameText.setText(name);
        placesView.phoneText.setText(phone);
        placesView.addressText.setText(address);
        placesView.hoursText.setText(open);

        // Setup Click Handlers
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()) {
                    case R.id.placeImage: case R.id.nameText:
                    case R.id.tableLayout:
                        Intent intent = new Intent(mContext, PlaceDetailActivity.class);
                        intent.putExtra(mContext.getString(R.string.key_business_id), businessId);
                        mContext.startActivity(intent);
                        break;
                    case R.id.menuDots:
                        View menuItemView = view.findViewById(R.id.menuDots);
                        PopupMenu popup = new PopupMenu(mContext, menuItemView);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.menu_places, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()) {
                                    case R.id.action_map:
                                        Intent mapIntent = new Intent(mContext, MapActivity.class);
                                        mapIntent.putExtra("key_place", place);
                                        mapIntent.putExtra("key_coords", coords);
                                        mContext.startActivity(mapIntent);
                                        break;
                                    case R.id.action_call:
                                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                        callIntent.setData(Uri.parse("tel:" + phone));
                                        mContext.startActivity(callIntent);
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                }
            }
        };
        placesView.menuDots.setOnClickListener(onClickListener);
        placesView.tableLayout.setOnClickListener(onClickListener);
        placesView.placeImage.setOnClickListener(onClickListener);
        placesView.nameText.setOnClickListener(onClickListener);

        // Load Images
        final Target target = new ResizableTarget(placesView.placeImage);
        placesView.placeImage.setTag(target);

        Picasso.with(mContext)
                .load(photo)
                .placeholder(R.drawable.cast_album_art_placeholder)
                .error(R.drawable.item_icon_parking)
                .transform(new FitToViewTransformation(placesView.placeImage))
                .into(target);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

}
