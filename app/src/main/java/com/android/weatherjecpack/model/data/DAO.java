package com.android.weatherjecpack.model.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.weatherjecpack.Utils.MyApplication;
import com.android.weatherjecpack.model.data.place.City;
import com.android.weatherjecpack.model.data.place.County;
import com.android.weatherjecpack.model.data.place.Province;
import com.android.weatherjecpack.model.data.weather.Weather;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.util.List;

import javax.inject.Inject;

public class DAO {

    @Inject
    public DAO(){}

    public void cacheProvinceList(List<Province> provinces) {
        if (provinces != null && provinces.size() != 0)
            LitePal.saveAll(provinces);
    }

    public void cacheCityList(List<City> cities){
        if (cities != null && cities.size() != 0)
            LitePal.saveAll(cities);
    }

    public void cacheCountyList(List<County> counties){
        if (counties != null && counties.size() != 0)
            LitePal.saveAll(counties);
    }

    public List<Province> getProvinces(){
        return LitePal.findAll(Province.class);
    }

    public List<City> getCities(String provinceId){
        return LitePal.where("provinceId = ?",provinceId).find(City.class);
    }

    public List<County> getCounties(String cityId){
        return LitePal.where("cityId = ?",cityId).find(County.class);
    }

    public void cacheWeather(Weather weather){
        String gsonWeather = new Gson().toJson(weather);
        SharedPreferences sharedPref = MyApplication.getContext().getSharedPreferences("a", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("weather",gsonWeather);
        editor.apply();
    }

    public Weather getCacheWeather(){
        SharedPreferences sharePref = MyApplication.getContext().getSharedPreferences("a",Context.MODE_PRIVATE);
        String gsonWeather = sharePref.getString("weather",null);
        return new Gson().fromJson(gsonWeather,Weather.class);
    }

}
