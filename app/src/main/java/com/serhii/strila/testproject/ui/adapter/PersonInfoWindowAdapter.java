package com.serhii.strila.testproject.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.serhii.strila.testproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Serhii Strila on 1/28/16
 */
public class PersonInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View v;
    private HashMap<Marker, String> mMap;
    private Marker mLastMarker;

    public PersonInfoWindowAdapter(Context context) {
        mMap = new HashMap<>();
        v = LayoutInflater.from(context).inflate(R.layout.item_map_info, null);
    }

    public void addMarker(Marker marker, String photo) {
        mMap.put(marker, photo);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        String url = mMap.get(marker);
        if (url != null) {
            ImageView imageView = (ImageView) v.findViewById(R.id.img_photo);
            if (marker.isInfoWindowShown() || marker.equals(mLastMarker)) {
                Picasso.with(v.getContext()).load(url).into(imageView);
            } else {
                mLastMarker = marker;
                Picasso.with(v.getContext()).load(url).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        marker.showInfoWindow();
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
            return v;
        }
        return null;
    }

}
