package com.nathansass.trace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nathansass.trace.deps.DaggerDeps;
import com.nathansass.trace.deps.Deps;
import com.nathansass.trace.network.NetworkModule;

import java.io.File;

public class BaseApp  extends AppCompatActivity {
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }
}