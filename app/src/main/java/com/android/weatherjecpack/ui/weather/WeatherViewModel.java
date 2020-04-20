package com.android.weatherjecpack.ui.weather;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.weatherjecpack.Utils.MyApplication;
import com.android.weatherjecpack.model.WeatherRepository;
import com.android.weatherjecpack.model.data.weather.Weather;

import javax.inject.Inject;

public class WeatherViewModel extends ViewModel {

    @Inject
    WeatherRepository repository;

    private String weatherId;

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Weather> weather = new MutableLiveData<>();

    public WeatherViewModel(){
        DaggerWeatherComponent.builder().appComponent(MyApplication.getContext().getAppComponent())
                .build().inject(this);
    }

    public void getWeather(){
        isLoading.setValue(true);
        repository.getWeather(weatherId, weather -> {
            this.weather.setValue(weather);
            isLoading.setValue(false);
        });
    }

    public void getWeather(String weatherId){
        this.weatherId = weatherId;
        getWeather();
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}

