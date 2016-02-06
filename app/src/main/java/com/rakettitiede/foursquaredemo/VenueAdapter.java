package com.rakettitiede.foursquaredemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class VenueAdapter extends ArrayAdapter<Venue> {
    private String mNoAddress;
    private String mDistanceFormat;

    public VenueAdapter(Context context, ArrayList<Venue> venues, String noAddress, String distanceFormat) {
        super(context, 0, venues);
        mNoAddress = noAddress;
        mDistanceFormat = distanceFormat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Venue venue = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.venue_list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(venue.getName());

        TextView address = (TextView) convertView.findViewById(R.id.address);
        address.setText(venue.getAddress() != null ? venue.getAddress() : mNoAddress);

        TextView distance = (TextView) convertView.findViewById(R.id.distance);
        distance.setText(String.format(mDistanceFormat, venue.getDistance()));

        return convertView;
    }
}
