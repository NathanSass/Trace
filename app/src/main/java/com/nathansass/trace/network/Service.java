package com.nathansass.trace.network;

import com.nathansass.trace.models.NearbyListResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getNearbyList(final GetNearbyListCallback callback) {
        return networkService.getNearbyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends NearbyListResponse>>() {
                    @Override
                    public Observable<? extends NearbyListResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<NearbyListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(NearbyListResponse nearbyListResponse) {
                        callback.onSuccess(nearbyListResponse);
                    }
                });

    }

    public interface GetNearbyListCallback {
        void onSuccess(NearbyListResponse nearbyListResponse);

        void onError(NetworkError networkError);
    }

}
