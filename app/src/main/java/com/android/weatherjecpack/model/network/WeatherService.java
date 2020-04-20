package com.android.weatherjecpack.model.network;

import com.android.weatherjecpack.model.data.place.City;
import com.android.weatherjecpack.model.data.place.County;
import com.android.weatherjecpack.model.data.place.Province;
import com.android.weatherjecpack.model.data.weather.HeWeather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("api/weather")
    Call<HeWeather> getWeather(@Query("cityid")String weatherId);

    @GET("api/china")
    Call<List<Province>> getProvinces();

    @GET("api/china/{provinceId}")
    Call<List<City>> getCities(@Path("provinceId")String provinceId);

    @GET("api/china/{provinceId}/{cityId}")
    Call<List<County>> getCounties(@Path("provinceId")String provinceId,@Path("cityId")String cityId);

}
