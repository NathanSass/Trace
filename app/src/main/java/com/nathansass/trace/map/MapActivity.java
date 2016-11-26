package com.nathansass.trace.map;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.nathansass.trace.BaseApp;
import com.nathansass.trace.R;
import com.nathansass.trace.network.Service;

import javax.inject.Inject;

public class MapActivity extends BaseApp implements MapCoolView {

    private MapView mapboxView;
    @Inject
    public Service service;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView(savedInstanceState);

        MapPresenter mapPresenter = new MapPresenter(service, this);

    }

    @Override
    public void renderView(Bundle savedInstanceState) {
        MapboxAccountManager.start(this, "pk.eyJ1IjoibmF0aGFuc2FzcyIsImEiOiJjaXZ5bTIwemMwMW45MnRtZG1jeDRiNnN6In0.KAgw7nGXvBK6x2Q1gAlBqQ");
        setContentView(R.layout.activity_map);
        progressBar = (ProgressBar) findViewById(R.id.pbProgress);
        showWait();
        mapboxView = (MapView) findViewById(R.id.mapview);
        mapboxView.onCreate(savedInstanceState);
        mapboxView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                removeWait();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapboxView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapboxView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapboxView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapboxView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapboxView.onSaveInstanceState(outState);
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }
}
