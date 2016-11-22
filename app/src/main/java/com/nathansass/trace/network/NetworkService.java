package com.nathansass.trace.network;

import com.nathansass.trace.models.CityListResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface NetworkService {

    @GET("v1/city")
    Observable<CityListResponse> getCityList();

}
