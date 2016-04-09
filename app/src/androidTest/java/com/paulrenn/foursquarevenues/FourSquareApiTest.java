package com.paulrenn.foursquarevenues;

import android.test.AndroidTestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FourSquareApiTest extends AndroidTestCase {
    final static String NAME = "Rakettitukku (väliaikainen)";
    final static String ADDRESS = "Kauppakeskus Kamppi 1.krs";
    final static long DISTANCE = 484L;

    public void testOnResponse() throws IOException, JSONException {
        String s = TestUtil.readTestFile(this, "test.json");
        JSONObject json = new JSONObject(s);
        final VenueModel[] venueResponse = {null};
        FourSquareApi api = new FourSquareApi(getContext(), new FourSquareApi.Listener() {
            @Override
            public void onVenueResponse(VenueModel venues) {
                venueResponse[0] = venues;
            }

            @Override
            public void onVenueError(String errorMessage) {

            }
        });
        api.onResponse(json);
        VenueModel venueModel = venueResponse[0];
        assertNotNull(venueModel);
        assertEquals(1, venueModel.size());
        Venue venue = venueModel.get(0);
        assertEquals(NAME, venue.getName());
        assertEquals(ADDRESS, venue.getAddress());
        assertEquals(DISTANCE, venue.getDistance());
    }
}
