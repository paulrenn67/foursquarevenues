package com.rakettitiede.foursquaredemo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Venue {
    private final static String LOG_TAG = Venue.class.getSimpleName();

    private String mName;
    private String mAddress;
    private long mDistance;

    public Venue(String name, String address, long distance) {
        mName = name;
        mAddress = address;
        mDistance = distance;
    }

    public static Venue parse(JSONObject json) throws JSONException {
        String name = json.getString("name");
        String address = null;
        JSONObject location = json.getJSONObject("location");
        try {
            // Apparently some responses are missing the address.
            address = location.getString("address");
        } catch (JSONException e) {
            Log.w(LOG_TAG, "No address for " + name);
        }
        long distance = location.getLong("distance");
        return new Venue(name, address, distance);
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public long getDistance() {
        return mDistance;
    }

    public void setDistance(long mDistance) {
        this.mDistance = mDistance;
    }
}
