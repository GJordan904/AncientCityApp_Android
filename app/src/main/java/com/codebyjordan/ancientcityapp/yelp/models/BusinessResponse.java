package com.codebyjordan.ancientcityapp.yelp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BusinessResponse {

    boolean is_claimed;
    double rating;
    String mobile_url;
    String rating_img_url;
    int review_count;
    String name;
    String rating_img_url_small;
    String url;
    String[][] categories;
    Review[] reviews;
    String phone;
    String snippet_text;
    String image_url;
    String snippet_image_url;
    String display_phone;
    String rating_img_url_large;
    String id;
    boolean is_closed;
    BusinessLocation location;

    public static BusinessResponse parseJson(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, BusinessResponse.class);
    }

    public String formattedPhone() {
        StringBuilder builder = new StringBuilder(getPhone())
                .insert(0, '(')
                .insert(4, ')')
                .insert(8, '-');
        return builder.toString();
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

    public Review[] getReviews() {
        return reviews;
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

    public String getId() {
        return id;
    }

    public boolean is_closed() {
        return is_closed;
    }

    public BusinessLocation getLocation() {
        return location;
    }
}
