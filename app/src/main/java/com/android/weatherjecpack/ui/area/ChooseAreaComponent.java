package com.android.weatherjecpack.ui.area;

import com.android.weatherjecpack.di.AppComponent;
import com.android.weatherjecpack.di.Single;

import dagger.Component;

@Single
@Component(dependencies = AppComponent.class)
public interface ChooseAreaComponent {
    void inject(ChooseAreaViewModel viewModel);
}
