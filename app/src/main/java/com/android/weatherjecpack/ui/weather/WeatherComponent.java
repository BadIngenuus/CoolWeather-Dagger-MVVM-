package com.android.weatherjecpack.ui.weather;

import com.android.weatherjecpack.di.AppComponent;
import com.android.weatherjecpack.di.Single;

import dagger.Component;

@Single
@Component(dependencies = AppComponent.class)
public interface WeatherComponent {
    void inject(WeatherViewModel viewModel);
}
