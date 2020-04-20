package com.android.weatherjecpack.model;

import com.android.weatherjecpack.Utils.AppExecutors;
import com.android.weatherjecpack.model.data.DAO;
import com.android.weatherjecpack.model.data.place.City;
import com.android.weatherjecpack.model.data.place.County;
import com.android.weatherjecpack.model.data.place.Province;
import com.android.weatherjecpack.model.data.weather.HeWeather;
import com.android.weatherjecpack.model.data.weather.Weather;
import com.android.weatherjecpack.model.network.WeatherNetWork;

import java.util.List;
import java.util.concurrent.Future;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private DAO DAO;

    private WeatherNetWork netWork;

    private AppExecutors executors;

    @Inject
    public WeatherRepository(DAO DAO,WeatherNetWork netWork,AppExecutors executors){
        this.DAO = DAO;
        this.netWork = netWork;
        this.executors = executors;
    }

    public void getProvinces(GetPlaceDataCall getPlaceDataCall){
        Runnable DaoRunnable = () -> {
            List<Province> provinces = DAO.getProvinces();
            if (provinces != null && provinces.size() != 0){
                executors.getMainThread().execute(() -> {
                    getPlaceDataCall.GetData(provinces);
                });
            }else {
                netWork.fetchProvinces(new Callback<List<Province>>() {
                    @Override
                    public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                        getPlaceDataCall.GetData(response.body());
                        DAO.cacheProvinceList(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Province>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        };
        executors.getDiskIO().execute(DaoRunnable);
    }

    public void getCities(String provinceId, GetPlaceDataCall getPlaceDataCall){
        Runnable DaoRunnable = () -> {
            List<City> cacheCities = DAO.getCities(provinceId);
            if (cacheCities != null && cacheCities.size() != 0){
                executors.getMainThread().execute(() -> {
                    getPlaceDataCall.GetData(cacheCities);
                });
            }else {
                netWork.fetchCities(new Callback<List<City>>() {
                    @Override
                    public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                        for (City c : response.body())
                            c.setProvinceId(provinceId);
                        getPlaceDataCall.GetData(response.body());
                        DAO.cacheCityList(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<City>> call, Throwable t) {
                        t.printStackTrace();
                    }
                },provinceId);
            }
        };
        executors.getDiskIO().execute(DaoRunnable);
    }

    public void getCounties(String provinceId, String cityId, GetPlaceDataCall getPlaceDataCall){
        Runnable DaoRunnable = () -> {
            List<County> cacheCounties = DAO.getCounties(cityId);
            if (cacheCounties.size() != 0 &&cacheCounties != null){
                executors.getMainThread().execute(() -> {
                    getPlaceDataCall.GetData(cacheCounties);
                });
            }else {
                netWork.fetchCounties(new Callback<List<County>>() {
                    @Override
                    public void onResponse(Call<List<County>> call, Response<List<County>> response) {
                        for (County c : response.body())
                            c.setCityId(cityId);
                        getPlaceDataCall.GetData(response.body());
                        DAO.cacheCountyList(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<County>> call, Throwable t) {
                        t.printStackTrace();
                    }
                },provinceId,cityId);
            }
        };
        executors.getDiskIO().execute(DaoRunnable);
    }

    public void getWeather(String weatherId,GetWeatherCall weatherCall){
        Runnable runnable = () -> {
            netWork.fetchWeather(new Callback<HeWeather>() {
                @Override
                public void onResponse(Call<HeWeather> call, Response<HeWeather> response) {
                    weatherCall.GetWeather(response.body().weather.get(0));
                    DAO.cacheWeather(response.body().weather.get(0));
                }

                @Override
                public void onFailure(Call<HeWeather> call, Throwable t) {
                    t.printStackTrace();
                }
            },weatherId);
        };
        executors.getDiskIO().execute(runnable);
    }

    public Weather getCacheWeather(){
        return DAO.getCacheWeather();
    }

    public interface GetPlaceDataCall<T>{
        void GetData(List<T> list);
    }

    public interface GetWeatherCall{
        void GetWeather(Weather weather);
    }

}
