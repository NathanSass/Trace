package com.nathansass.trace.deps;

import com.nathansass.trace.home.HomeActivity;
import com.nathansass.trace.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}