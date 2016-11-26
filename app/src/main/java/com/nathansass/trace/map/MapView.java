package com.nathansass.trace.map;

/**
 * Created by nathansass on 11/25/16.
 */

public interface MapView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void renderView();
}

