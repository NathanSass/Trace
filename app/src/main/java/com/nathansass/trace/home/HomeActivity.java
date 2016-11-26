package com.nathansass.trace.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nathansass.trace.BaseApp;
import com.nathansass.trace.R;
import com.nathansass.trace.models.NearbyListData;
import com.nathansass.trace.models.NearbyListResponse;
import com.nathansass.trace.network.Service;

import javax.inject.Inject;

public class HomeActivity extends BaseApp implements HomeView {
    private RecyclerView rvList;
    @Inject
    public Service service;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView();
        init();

        HomePresenter presenter = new HomePresenter(service, this);
        presenter.getNearbyList();
    }

    public void renderView() {
        setContentView(R.layout.activity_main);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        progressBar = (ProgressBar) findViewById(R.id.pbProgress);
        rvList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });
    }

    public void init() {
        rvList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void getNearbyListSuccess(NearbyListResponse nearbyListResponse) {
        HomeAdapter adapter = new HomeAdapter(getApplicationContext(), nearbyListResponse.getData(),
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(NearbyListData Item) {
                        Toast.makeText(getApplicationContext(), Item.getCategory(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        rvList.setAdapter(adapter);
    }

}
