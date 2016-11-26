package com.nathansass.trace.map;

import com.nathansass.trace.models.NearbyListResponse;
import com.nathansass.trace.network.NetworkError;
import com.nathansass.trace.network.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nathansass on 11/25/16.
 */

public class MapPresenter {
    private final Service service;
    private final MapCoolView view;
    private CompositeSubscription subscriptions;

    public MapPresenter(Service service, MapCoolView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getNearbyItemsForMap() {
        //network call here then pass in items to main activity to display them on the map\
        view.showWait();
        Subscription subscription = service.getNearbyList(new Service.GetNearbyListCallback() {
            @Override
            public void onSuccess(NearbyListResponse nearbyListResponse) {
                view.removeWait();
                view.getMapItemsListSuccess(nearbyListResponse);
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
