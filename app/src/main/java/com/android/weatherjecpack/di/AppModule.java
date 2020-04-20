package com.android.weatherjecpack.di;

import com.android.weatherjecpack.Utils.AppExecutors;
import com.android.weatherjecpack.model.WeatherRepository;
import com.android.weatherjecpack.model.data.DAO;
import com.android.weatherjecpack.model.network.ServiceCreator;
import com.android.weatherjecpack.model.network.WeatherNetWork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    public ServiceCreator providerServiceCreator(){
        return new ServiceCreator();
    }

    @Singleton
    @Provides
    public WeatherNetWork providerNetWork(ServiceCreator serviceCreator){
        return new WeatherNetWork(serviceCreator);
    }

    @Singleton
    @Provides
    public DAO providerDAO(){
        return new DAO();
    }

    @Singleton
    @Provides
    public AppExecutors providerAppExecutors(){
        return new AppExecutors();
    }

    @Singleton
    @Provides
    public WeatherRepository providerRepository(WeatherNetWork netWork,AppExecutors executors,DAO dao){
        return new WeatherRepository(dao,netWork,executors);
    }

}
