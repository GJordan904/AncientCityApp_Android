package com.codebyjordan.ancientcityapp.permissions;

import com.google.android.gms.maps.GoogleMap;

public class ResponseInterfaces {

    public interface LocationSuccessHandler {
        void responseMethod(GoogleMap gMap);
    }
}
