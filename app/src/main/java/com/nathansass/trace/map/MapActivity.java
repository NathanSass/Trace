package com.nathansass.trace.map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.nathansass.trace.BaseApp;
import com.nathansass.trace.R;
import com.nathansass.trace.models.NearbyListData;
import com.nathansass.trace.models.NearbyListResponse;
import com.nathansass.trace.network.Service;

import java.util.List;

import javax.inject.Inject;

public class MapActivity extends BaseApp implements MapCoolView {

    private MapView mapboxView;
    private MapPresenter mapPresenter;
    private ImageView ivNearbyImage;
    private TextView tvUsername;
    private Marker lastMarkerHighlighted = null;

    @Inject
    public Service service;
    ProgressBar progressBar;
    private Vibrator vibrator;

    private MapboxMap coolMap;

    private List<NearbyListData> nearbyListDatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView(savedInstanceState);

        mapPresenter = new MapPresenter(service, this);

    }

    @Override
    public void renderView(Bundle savedInstanceState) {
        MapboxAccountManager.start(this, "pk.eyJ1IjoibmF0aGFuc2FzcyIsImEiOiJjaXZ5bTIwemMwMW45MnRtZG1jeDRiNnN6In0.KAgw7nGXvBK6x2Q1gAlBqQ");
        setContentView(R.layout.activity_map);
        progressBar = (ProgressBar) findViewById(R.id.pbProgress);
        ivNearbyImage = (ImageView) findViewById(R.id.ivNearbyImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);

        showWait();
        mapboxView = (MapView) findViewById(R.id.mapview);
        mapboxView.onCreate(savedInstanceState);
        mapboxView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                setMarkerClickListener(mapboxMap);
                coolMap = mapboxMap;
                mapPresenter.getNearbyItemsForMap();
            }
        });
    }

    public void setMarkerClickListener(MapboxMap mapboxMap) {
        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                vibrate();
                setItemInView(Integer.parseInt(marker.getTitle()));
                changeMarkerColor(marker);
                return true;
            }
        });
    }

    public void changeMarkerColor(Marker marker) {
        if (lastMarkerHighlighted != null) {
            lastMarkerHighlighted.setIcon(getStandardIcon());
        }
        marker.setIcon(getSelectedIcon());
        lastMarkerHighlighted = marker;
    }

    public Icon getStandardIcon() {
        IconFactory iconFactory = IconFactory.getInstance(MapActivity.this);
        Drawable iconDrawable = ContextCompat.getDrawable(MapActivity.this, R.drawable.grey_marker);
        Icon icon = iconFactory.fromDrawable(iconDrawable);
        return icon;
    }

    public Icon getSelectedIcon() {
        IconFactory iconFactory = IconFactory.getInstance(MapActivity.this);
        Drawable iconDrawable = ContextCompat.getDrawable(MapActivity.this, R.drawable.yellow_marker);
        Icon icon = iconFactory.fromDrawable(iconDrawable);
        return icon;
    }

    public void setItemInView(int position) {
        NearbyListData currentItem = nearbyListDatas.get(position);
        tvUsername.setText(currentItem.getUsername());
        Glide.with(getApplicationContext())
                .load(currentItem.getUrl())
                .placeholder(ContextCompat.getDrawable(getApplicationContext(), R.drawable.placeholder_long))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivNearbyImage);
    }

    @Override
    public void getMapItemsListSuccess(NearbyListResponse nearbyListResponse) {
        nearbyListDatas = nearbyListResponse.getData();
        for (int i = 0; i < nearbyListDatas.size(); i++) {
            NearbyListData currentPlace = nearbyListDatas.get(i);
            buildMarker(currentPlace.getLat(), currentPlace.getLng(), i);
        }

        // Set the first item
        Marker marker = getMap().getMarkers().get(0);
        setItemInView(0);
        changeMarkerColor(marker);
    }

    @Override
    public void buildMarker(Double lat, Double lng, int position) {
        getMap().addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(position + "")
                .snippet("None currently")
                .icon(getStandardIcon()));
    }

    public MapboxMap getMap() {
        return coolMap;
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
    public void vibrate() {
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(75);
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
