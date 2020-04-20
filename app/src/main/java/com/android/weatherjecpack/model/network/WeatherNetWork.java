package com.android.weatherjecpack.model.network;

import com.android.weatherjecpack.model.data.place.City;
import com.android.weatherjecpack.model.data.place.County;
import com.android.weatherjecpack.model.data.place.Province;
import com.android.weatherjecpack.model.data.weather.HeWeather;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;

public class WeatherNetWork {

    private ServiceCreator serviceCreator;
    private WeatherService weatherService;

    @Inject
    public WeatherNetWork(ServiceCreator serviceCreator){
        this.serviceCreator = serviceCreator;
        weatherService = serviceCreator.create(WeatherService.class);
    }

    public void fetchCities(Callback<List<City>> callback, String provinceId){
        weatherService.getCities(provinceId).enqueue(callback);
    }

    public void fetchProvinces(Callback<List<Province>> callback){
        weatherService.getProvinces().enqueue(callback);
    }

    public void fetchCounties(Callback<List<County>> callback,String provinceId,String cityId){
        weatherService.getCounties(provinceId,cityId).enqueue(callback);
    }

    public void fetchWeather(Callback<HeWeather> callback,String weatherId){
        weatherService.getWeather(weatherId).enqueue(callback);
    }

}
