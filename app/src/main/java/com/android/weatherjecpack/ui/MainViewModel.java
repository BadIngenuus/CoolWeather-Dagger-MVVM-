package com.android.weatherjecpack.ui;

import androidx.lifecycle.ViewModel;

import com.android.weatherjecpack.Utils.MyApplication;
import com.android.weatherjecpack.model.WeatherRepository;
import com.android.weatherjecpack.model.data.weather.Weather;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    @Inject
    WeatherRepository repository;

    String weatherId;

    public MainViewModel(){
        DaggerMainComponent.builder().appComponent(MyApplication.getContext().getAppComponent()).build().inject(this);
    }

    public boolean getIsCacheWeather(){
        Weather weather = repository.getCacheWeather();
        if (weather != null) {
            weatherId = weather.basic.weatherId;
            return true;
        }
        else return false;
    }

}
