package com.nathansass.trace.home;

import com.nathansass.trace.models.CityListResponse;

public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getCityListSuccess(CityListResponse cityListResponse);

}
