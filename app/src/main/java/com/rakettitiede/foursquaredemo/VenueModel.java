package com.rakettitiede.foursquaredemo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class VenueModel extends ArrayList<Venue> {
    private final static String LOG_TAG = VenueModel.class.getSimpleName();

    public VenueModel() {
    }

    public VenueModel(JSONArray json) {
        for (int i = 0; i < json.length(); i++) {
            try {
                add(Venue.parse(json.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error parsing venue", e);
            }
        }
    }
}
