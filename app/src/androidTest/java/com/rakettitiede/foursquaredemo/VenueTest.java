package com.rakettitiede.foursquaredemo;

import android.test.AndroidTestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class VenueTest extends AndroidTestCase {

    final static String NAME = "name";
    final static String ADDRESS = "address";
    final static String NO_ADDRESS = null;
    final static long DISTANCE = 69L;

    public void testConstructor() {
        Venue venue = new Venue(NAME, ADDRESS, DISTANCE);
        assertEquals(venue.getName(), NAME);
        assertEquals(venue.getAddress(), ADDRESS);
        assertEquals(venue.getDistance(), DISTANCE);
    }

    public void testParseWithValidAddress() throws JSONException {
        String s = "{ 'name':'" + NAME + "', " +
                "'location':{'address':'" + ADDRESS + "', " +
                "'distance':"+ DISTANCE + "}}";
        JSONObject json = new JSONObject(s);
        Venue venue = Venue.parse(json);
        assertEquals(venue.getName(), NAME);
        assertEquals(venue.getAddress(), ADDRESS);
        assertEquals(venue.getDistance(), DISTANCE);
    }

    public void testParseWithInvalidAddress() throws JSONException {
        String s = "{ 'name':'" + NAME + "', " +
                "'location':{'distance':"+ DISTANCE + "}}";
        JSONObject json = new JSONObject(s);
        Venue venue = Venue.parse(json);
        assertEquals(venue.getName(), NAME);
        assertEquals(venue.getAddress(), NO_ADDRESS);
        assertEquals(venue.getDistance(), DISTANCE);
    }
}
