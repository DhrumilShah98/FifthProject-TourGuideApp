package com.example.dhrumilshah.tourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class LocationAdapter extends ArrayAdapter<Location>{

    private Context context;
    LocationAdapter( Context context,  ArrayList<Location> locations) {
        super(context, 0, locations);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_list_item_view, parent, false);
        }
        Location currentWord = getItem(position);
        ImageView mImageView = listItemView.findViewById((R.id.location_image));
        if(currentWord != null) {
            mImageView.setImageResource(currentWord.getImageResourceId());

            TextView mTextView = listItemView.findViewById(R.id.location_name);
            mTextView.setText(context.getString(currentWord.getNameStringId()));

            mTextView = listItemView.findViewById(R.id.location_address);
            mTextView.setText(context.getString(currentWord.getAddressStringId()));
        }
        return listItemView;
    }
}
