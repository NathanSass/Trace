package com.nathansass.trace.map;

import android.os.Bundle;

import com.nathansass.trace.models.NearbyListResponse;

/**
 * Created by nathansass on 11/25/16.
 */

public interface MapCoolView {
    void showWait();

    void removeWait();

    void getMapItemsListSuccess(NearbyListResponse nearbyListResponse);

    void onFailure(String appErrorMessage);

    void renderView(Bundle savedInstanceState);

    void vibrate();

    void buildMarker(Double lat, Double lng, int position);
}

