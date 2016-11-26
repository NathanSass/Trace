package com.nathansass.trace.map;

import android.os.Bundle;

/**
 * Created by nathansass on 11/25/16.
 */

public interface MapCoolView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void renderView(Bundle savedInstanceState);
}

