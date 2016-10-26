package com.codebyjordan.ancientcityapp.yelp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Business implements Parcelable {
    boolean is_claimed;
    double rating;
    String mobile_url;
    String rating_img_url;
    int review_count;
    String name;
    String rating_img_url_small;
    String url;
    String[][] categories;
    String phone;
    String snippet_text;
    String image_url;
    String snippet_image_url;
    String display_phone;
    String rating_img_url_large;
    String id;
    boolean is_closed;
    BusinessLocation location;

    public String getId() {
        return id;
    }

    public boolean is_claimed() {
        return is_claimed;
    }

    public double getRating() {
        return rating;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public String getRating_img_url() {
        return rating_img_url;
    }

    public int getReview_count() {
        return review_count;
    }

    public String getName() {
        return name;
    }

    public String getRating_img_url_small() {
        return rating_img_url_small;
    }

    public String getUrl() {
        return url;
    }

    public String[][] getCategories() {
        return categories;
    }

    public String getPhone() {
        return phone;
    }

    public String getSnippet_text() {
        return snippet_text;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getSnippet_image_url() {
        return snippet_image_url;
    }

    public String getDisplay_phone() {
        return display_phone;
    }

    public String getRating_img_url_large() {
        return rating_img_url_large;
    }

    public boolean is_closed() {
        return is_closed;
    }

    public BusinessLocation getLocation() {
        return location;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
    }

    private Business(Parcel in) {
        name = in.readString();
        id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Business> CREATOR
            = new Parcelable.Creator<Business>() {

        @Override
        public Business createFromParcel(Parcel source) {
            return new Business(source);
        }

        @Override
        public Business[] newArray(int size) {
            return new Business[size];
        }
    };
}
