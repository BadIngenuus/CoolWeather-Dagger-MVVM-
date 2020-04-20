package com.android.weatherjecpack.ui.area;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.weatherjecpack.Utils.MyApplication;
import com.android.weatherjecpack.model.WeatherRepository;
import com.android.weatherjecpack.model.data.place.City;
import com.android.weatherjecpack.model.data.place.County;
import com.android.weatherjecpack.model.data.place.Province;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChooseAreaViewModel extends ViewModel {

    @Inject
    WeatherRepository repository;

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<AreaLevel> currentLevel = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSelectFinish = new MutableLiveData<>();
    public MutableLiveData<List<Province>> provinces = new MutableLiveData<>();
    public MutableLiveData<List<City>> cities = new MutableLiveData<>();
    public MutableLiveData<List<County>> counties = new MutableLiveData<>();

    public Province selectProvince;
    public City selectCity;
    public County selectCounty;

    public ChooseAreaViewModel(){
        DaggerChooseAreaComponent.builder()
                .appComponent(MyApplication.getContext().getAppComponent()).build().inject(this);

    }

    public void getProvinces(){
        isLoading.setValue(true);
        currentLevel.setValue(AreaLevel.PROVINCE);
        repository.getProvinces(list -> {
            provinces.setValue(list);
            isLoading.setValue(false);
        });
    }

    public void getCities(){
        isLoading.setValue(true);
        currentLevel.setValue(AreaLevel.CITY);
        repository.getCities(selectProvince.getProvinceId(), list -> {
            cities.setValue(list);
            isLoading.setValue(false);
        });
    }

    public void getCounties(){
        isLoading.setValue(true);
        currentLevel.setValue(AreaLevel.COUNTY);
        repository.getCounties(selectProvince.getProvinceId(),selectCity.getCityId()
                ,list -> {
                    counties.setValue(list);
                    isLoading.setValue(false);
                });
    }

    public void onClickBack(){
        if (currentLevel.getValue() == AreaLevel.COUNTY){
            getCities();
        }else if (currentLevel.getValue() == AreaLevel.CITY){
            getProvinces();
        }
    }

    public void provinceItemClick(String areaName){
        if (provinces != null){
            for (Province p:provinces.getValue()){
                if (p.getProvinceName().equals(areaName)){
                    selectProvince = p;
                    getCities();
                }
            }
        }
    }

    public void cityItemClick(String areaName){
        if (cities != null){
            for (City c:cities.getValue()){
                if (c.getCityName().equals(areaName)){
                    selectCity = c;
                    getCounties();
                }
            }
        }
    }

    public void countyItemClick(String areaName){
        if (counties.getValue() != null){
            for (County c:counties.getValue()){
                if (c.getCountryName().equals(areaName)){
                    selectCounty = c;
                    isSelectFinish.setValue(true);
                }
            }
        }
    }

    public List<String> convertProvinces(@NotNull List<Province> provinces){
        List<String> strings = new ArrayList<>();
        for (Province province:provinces)
            strings.add(province.getProvinceName());
        return strings;
    }

    public List<String> convertCities(@NotNull List<City> cities){
        List<String> strings = new ArrayList<>();
        for (City c:cities)
            strings.add(c.getCityName());
        return strings;
    }

    public List<String> convertCounties(@NotNull List<County> counties){
        List<String> strings = new ArrayList<>();
        for (County county:counties)
            strings.add(county.getCountryName());
        return strings;
    }

    public enum AreaLevel{
        PROVINCE,
        CITY,
        COUNTY
    }
}
