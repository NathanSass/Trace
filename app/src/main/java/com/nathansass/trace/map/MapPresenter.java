package com.nathansass.trace.map;

import com.nathansass.trace.network.Service;

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

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
