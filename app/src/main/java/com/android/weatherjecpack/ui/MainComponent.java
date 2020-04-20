package com.android.weatherjecpack.ui;

import com.android.weatherjecpack.di.AppComponent;
import com.android.weatherjecpack.di.Single;

import dagger.Component;

@Single
@Component(dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainViewModel viewModel);

}
