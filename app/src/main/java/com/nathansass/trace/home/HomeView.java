package com.nathansass.trace.home;

import com.nathansass.trace.models.NearbyListResponse;

public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getNearbyListSuccess(NearbyListResponse nearbyListResponse);

    void hideKeyboard();

}
