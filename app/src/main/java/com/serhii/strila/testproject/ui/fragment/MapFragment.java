package com.serhii.strila.testproject.ui.fragment;


import android.os.Bundle;
import android.support.annotation.MainThread;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.serhii.strila.testproject.R;
import com.serhii.strila.testproject.Utils;
import com.serhii.strila.testproject.model.Person;
import com.serhii.strila.testproject.ui.adapter.PersonInfoWindowAdapter;

import org.testpackage.test_sdk.android.testlib.API;
import org.testpackage.test_sdk.android.testlib.services.UpdateService;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

public class MapFragment extends BaseFragment implements OnMapReadyCallback,
        UpdateService.UpdateServiceListener {

    @Bind(R.id.mapView)
    MapView mMapView;
    private GoogleMap mMap;
    private LongSparseArray<Marker> mMarkers = new LongSparseArray<>();
    private PersonInfoWindowAdapter mAdapter;

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        API.INSTANCE.unSubscribeUpdates();
        mMapView.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onChanges(String person) {
        Person personBd = mGson.fromJson(person, Person.class);
        mMapView.post(() -> createMarker(personBd));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, rootView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mAdapter = new PersonInfoWindowAdapter(getContext());
        mMap.setInfoWindowAdapter(mAdapter);
        initBase();
        API.INSTANCE.subscribeUpdates(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    private void initBase() {
        mRealm.where(Person.class).findAllSorted(Person.ID).asObservable()
                .flatMap(Observable::from)
                .doOnNext(this::createMarker)
                .subscribe();
    }

    @MainThread
    private void createMarker(Person person) {
        Marker markerOld = mMarkers.get(person.getId());
        LatLng location = Utils.getCoordinates(person.getLocation());
        if (location == null) {
            return;
        }
        if (markerOld != null) {
            markerOld.setPosition(location);
        } else {
            Marker marker = mMap.addMarker(new MarkerOptions().position(location)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
            mMarkers.append(person.getId(), marker);
            mAdapter.addMarker(marker, person.getPhoto());
        }
    }
}
