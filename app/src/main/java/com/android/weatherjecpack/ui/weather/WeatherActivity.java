package com.android.weatherjecpack.ui.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.android.weatherjecpack.R;
import com.android.weatherjecpack.Utils.GlideUtil;
import com.android.weatherjecpack.databinding.ActivityWeatherBinding;
import com.android.weatherjecpack.model.data.weather.Forecast;
import com.android.weatherjecpack.model.data.weather.Weather;

import org.jetbrains.annotations.NotNull;

public class WeatherActivity extends AppCompatActivity {

    public static final String Bing_url = "https://bing.ioliu.cn/v1/?w=720&h=1280";

    public WeatherViewModel viewModel;
    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWeatherBinding binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initUi(binding);
        observe(binding);
        getWeather();
    }

    public void initUi(ActivityWeatherBinding binding){
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            binding.suggestion.sport.image.setImageDrawable(getDrawable(R.drawable.ic_sport));
            binding.suggestion.comf.image.setImageDrawable(getDrawable(R.drawable.ic_comf));
            binding.suggestion.carWash.image.setImageDrawable(getDrawable(R.drawable.ic_local_car_wash));
        }
        drawerLayout = binding.weatherDrawer;
        GlideUtil.glideImage(this, Bing_url,binding.weatherBackground);
        binding.title.navButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    public void getWeather(){
        Intent intent = getIntent();
        String weatherId = intent.getStringExtra("weather_id");
        viewModel.setWeatherId(weatherId);
        viewModel.getWeather();
    }


    public void observe(@NotNull ActivityWeatherBinding binding){
        viewModel.isLoading.observe(this,aBoolean -> {
            if (aBoolean) binding.swipeRefresh.setRefreshing(true);
            else binding.swipeRefresh.setRefreshing(false);
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.getWeather();
        });

        viewModel.weather.observe(this, weather -> {
            binding.title.weatherCity.setText(weather.basic.cityName);
            binding.title.updateTime.setText(weather.basic.update.slipTime());
            binding.now.temperatureText.setText(weather.now.temperature);
            binding.now.weatherInfoText.setText(weather.now.more.info);
            binding.aqi.aqiValue.setText(weather.aqi.city.aqi);
            binding.aqi.pmValue.setText(weather.aqi.city.pm25);
            initForecastWeather(binding,weather);
            initSuggestion(binding,weather);
        });
    }

    public void initForecastWeather(@NotNull ActivityWeatherBinding binding, @NotNull Weather weather){
        Forecast tomorrow = weather.forecastList.get(0);
        Forecast acquired = weather.forecastList.get(1);
        Forecast dayAfterTomorrow = weather.forecastList.get(2);
        binding.forecast.tomorrow.cond.setText(tomorrow.more.info);
        binding.forecast.tomorrow.time.setText(tomorrow.data);
        binding.forecast.tomorrow.maxTemp.setText(tomorrow.temperature.max);
        binding.forecast.tomorrow.minTemp.setText(tomorrow.temperature.min);
        binding.forecast.acquired.cond.setText(acquired.more.info);
        binding.forecast.acquired.time.setText(acquired.data);
        binding.forecast.acquired.minTemp.setText(acquired.temperature.min);
        binding.forecast.acquired.maxTemp.setText(acquired.temperature.max);
        binding.forecast.theDayAfterTomorrow.cond.setText(dayAfterTomorrow.more.info);
        binding.forecast.theDayAfterTomorrow.time.setText(dayAfterTomorrow.data);
        binding.forecast.theDayAfterTomorrow.maxTemp.setText(dayAfterTomorrow.temperature.max);
        binding.forecast.theDayAfterTomorrow.minTemp.setText(dayAfterTomorrow.temperature.min);
    }

    private void initSuggestion(@NotNull ActivityWeatherBinding binding, @NotNull Weather weather){
        binding.suggestion.carWash.text.setText(weather.suggestion.carwash.info);
        binding.suggestion.comf.text.setText(weather.suggestion.comfort.info);
        binding.suggestion.sport.text.setText(weather.suggestion.sport.info);
    }

}
