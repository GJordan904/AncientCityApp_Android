/*

Copyright 2015 Akexorcist

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package com.codebyjordan.ancientcityapp.directions.request;

import com.codebyjordan.ancientcityapp.directions.DirectionCallback;
import com.codebyjordan.ancientcityapp.directions.model.Direction;
import com.codebyjordan.ancientcityapp.directions.network.DirectionAndPlaceConnection;
import com.codebyjordan.ancientcityapp.directions.request.DirectionRequestParam;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by Akexorcist on 11/29/15 AD.
 */
public class DirectionRequest {
    protected DirectionRequestParam param;

    public DirectionRequest(String apiKey, LatLng origin, LatLng destination) {
        param = new DirectionRequestParam().setApiKey(apiKey).setOrigin(origin).setDestination(destination);
    }

    public DirectionRequest transportMode(String transportMode) {
        param.setTransportMode(transportMode);
        return this;
    }

    public DirectionRequest language(String language) {
        param.setLanguage(language);
        return this;
    }

    public DirectionRequest unit(String unit) {
        param.setUnit(unit);
        return this;
    }

    public DirectionRequest avoid(String avoid) {
        String oldAvoid = param.getAvoid();
        if (oldAvoid != null && !oldAvoid.isEmpty()) {
            oldAvoid += "|";
        } else {
            oldAvoid = "";
        }
        oldAvoid += avoid;
        param.setAvoid(oldAvoid);
        return this;
    }

    public DirectionRequest transitMode(String transitMode) {
        String oldTransitMode = param.getTransitMode();
        if (oldTransitMode != null && !oldTransitMode.isEmpty()) {
            oldTransitMode += "|";
        } else {
            oldTransitMode = "";
        }
        oldTransitMode += transitMode;
        param.setTransitMode(oldTransitMode);
        return this;
    }

    public DirectionRequest alternativeRoute(boolean alternative) {
        param.setAlternatives(alternative);
        return this;
    }

    public DirectionRequest departureTime(String time) {
        param.setDepartureTime(time);
        return this;
    }

    public Direction execute() {
        Direction output = new Direction();
        Call<Direction> direction = DirectionAndPlaceConnection.getInstance()
                .createService()
                .getDirection(param.getOrigin().latitude + "," + param.getOrigin().longitude,
                        param.getDestination().latitude + "," + param.getDestination().longitude,
                        param.getTransportMode(),
                        param.getDepartureTime(),
                        param.getLanguage(),
                        param.getUnit(),
                        param.getAvoid(),
                        param.getTransitMode(),
                        param.isAlternatives(),
                        param.getApiKey());

        try {
            output = direction.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
