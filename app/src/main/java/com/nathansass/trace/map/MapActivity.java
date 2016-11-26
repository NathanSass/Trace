package com.nathansass.trace.map;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.nathansass.trace.BaseApp;
import com.nathansass.trace.R;
import com.nathansass.trace.network.Service;

import javax.inject.Inject;

public class MapActivity extends BaseApp implements MapView {
    @Inject
    public Service service;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView();

        MapPresenter presenter = new MapPresenter(service, this);
    }

    @Override
    public void renderView() {
        setContentView(R.layout.activity_map);
        progressBar = (ProgressBar) findViewById(R.id.pbProgress);
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }
}
