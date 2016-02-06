package com.rakettitiede.foursquaredemo;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FourSquareApi implements Response.Listener<JSONObject>, Response.ErrorListener {
    private final static String LOG_TAG = FourSquareApi.class.getSimpleName();
    private final static String REQUEST_TAG = "tag";
    private final static String CLIENT_ID = "VYHHOZW120OOEXDKK10OWDZVBQFRBGYT1Z5FB25S3K2NGNT5";
    private final static String CLIENT_SECRET = "ZN2TYDIWYX2DQPRH2GVGV1BFBPVSTVRVA3T53LYD4WFY3IB3";

    private RequestQueue mRequestQueue;
    private LocationManager mLocationManager;
    private Listener mListener;

    public interface Listener {
        void onVenueResponse(VenueModel venues);
        void onVenueError(String errorMessage);
    }

    public FourSquareApi(Context context, Listener listener) {
        mRequestQueue = Volley.newRequestQueue(context);
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mListener = listener;
    }

    public void searchVenues(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            mListener.onVenueResponse(new VenueModel());
            return;
        }

        Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            mListener.onVenueError("No location available");
            return;
        }

        // Client ID: xxxx
        // Client Secret: zzzz
        // Access Token URL: https://foursquare.com/oauth2/access_token
        // Authorize URL: https://foursquare.com/oauth2/authorize
        String url = "https://api.foursquare.com/v2/venues/search" +
                "?client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET +
                "&v=20160206" +
                "&ll=" + location.getLatitude() + "," + location.getLongitude() +
                "&query=" + keyword;

        JsonObjectRequest request = new JsonObjectRequest(url, null, this, this);
        request.setTag(REQUEST_TAG);

        // Cancel any outstanding requests, then make new request with the new search string.
        cancelAll();
        mRequestQueue.add(request);
    }

    @Override
    public void onResponse(JSONObject json) {
        Log.d(LOG_TAG, json.toString());
        try {
            JSONObject response = json.getJSONObject("response");
            JSONArray venues = response.getJSONArray("venues");
            Log.d(LOG_TAG, "There are " + venues.length() + " venues in response");
            mListener.onVenueResponse(new VenueModel(venues));
        } catch (JSONException e) {
            mListener.onVenueError(e.getMessage());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage;
        if (error.networkResponse != null) {
            errorMessage  = "Status Code " + error.networkResponse.statusCode +
                    (error.getMessage() != null ? " " + error.getMessage() : "");
        }else {
            errorMessage = error.getMessage();
        }
        mListener.onVenueError(errorMessage);
    }

    public void cancelAll() {
        mRequestQueue.cancelAll(REQUEST_TAG);
    }
}
