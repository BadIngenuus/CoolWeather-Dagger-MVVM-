package com.android.weatherjecpack.model.network;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {

    private static final String url = "http://guolin.tech/";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Inject
    public ServiceCreator(){}

    public <T> T create(Class<T> tClass){
        return retrofit.create(tClass);
    }

}
