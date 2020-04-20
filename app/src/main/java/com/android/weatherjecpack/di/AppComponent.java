package com.android.weatherjecpack.di;

import com.android.weatherjecpack.model.WeatherRepository;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    WeatherRepository getRepository();

}
