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

    private static class ViewHolder {
        TextView mName;
        TextView mAddress;
        TextView mDistance;
    }

    public VenueAdapter(Context context, ArrayList<Venue> venues, String noAddress, String distanceFormat) {
        super(context, 0, venues);
        mNoAddress = noAddress;
        mDistanceFormat = distanceFormat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Venue venue = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.venue_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mName =  (TextView) convertView.findViewById(R.id.name);
            viewHolder.mAddress = (TextView) convertView.findViewById(R.id.address);
            viewHolder.mDistance = (TextView) convertView.findViewById(R.id.distance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mName.setText(venue.getName());
        viewHolder.mAddress.setText(venue.getAddress() != null ? venue.getAddress() : mNoAddress);
        viewHolder.mDistance.setText(String.format(mDistanceFormat, venue.getDistance()));

        return convertView;
    }
}
