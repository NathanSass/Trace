package com.nathansass.trace.network;

import com.nathansass.trace.models.NearbyListResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface NetworkService {

    @GET("v1/nearby")
    Observable<NearbyListResponse> getNearbyList();

}
