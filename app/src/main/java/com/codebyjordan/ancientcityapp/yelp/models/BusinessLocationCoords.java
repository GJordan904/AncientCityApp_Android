package com.codebyjordan.ancientcityapp.yelp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BusinessLocationCoords implements Parcelable {
    double latitude;
    double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    private BusinessLocationCoords(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<BusinessLocationCoords> CREATOR
            = new Parcelable.Creator<BusinessLocationCoords>() {

        @Override
        public BusinessLocationCoords createFromParcel(Parcel source) {
            return new BusinessLocationCoords(source);
        }

        @Override
        public BusinessLocationCoords[] newArray(int size) {
            return new BusinessLocationCoords[size];
        }
    };
}
