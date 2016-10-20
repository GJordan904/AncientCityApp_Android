package com.codebyjordan.ancientcityapp.yelp.models;

public class Review {
    int rating;
    String excerpt;
    int time_created;
    String rating_img_url;
    String rating_img_small_url;
    User user;
    String rating_image_large_url;
    String id;

    public int getRating() {
        return rating;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public int getTime_created() {
        return time_created;
    }

    public String getRating_img_url() {
        return rating_img_url;
    }

    public String getRating_img_small_url() {
        return rating_img_small_url;
    }

    public User getUser() {
        return user;
    }

    public String getRating_image_large_url() {
        return rating_image_large_url;
    }

    public String getId() {
        return id;
    }
}
