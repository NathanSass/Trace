package com.nathansass.trace.home;

import com.nathansass.trace.models.CityListResponse;
import com.nathansass.trace.models.NearbyListResponse;
import com.nathansass.trace.network.NetworkError;
import com.nathansass.trace.network.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter {
    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;

    public HomePresenter(Service service, HomeView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getCityList() {
        view.showWait();

        Subscription subscription = service.getCityList(new Service.GetCityListCallback() {
            @Override
            public void onSuccess(CityListResponse cityListResponse) {
                view.removeWait();
                view.getCityListSuccess(cityListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void getNearbyList() {
        view.showWait();

        Subscription subscription = service.getNearbyList(new Service.GetNearbyListCallback() {
            @Override
            public void onSuccess(NearbyListResponse nearbyListResponse) {
                view.removeWait();
                view.getNearbyListSuccess(nearbyListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }
        });

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
