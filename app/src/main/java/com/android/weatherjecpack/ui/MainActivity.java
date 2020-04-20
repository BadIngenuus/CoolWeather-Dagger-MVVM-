package com.android.weatherjecpack.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.android.weatherjecpack.R;
import com.android.weatherjecpack.databinding.ActivityMainBinding;
import com.android.weatherjecpack.ui.area.ChooseAreaFragment;
import com.android.weatherjecpack.ui.weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if (viewModel.getIsCacheWeather()){
            Intent intent = new Intent(this, WeatherActivity.class);
            intent.putExtra("weather_id",viewModel.weatherId);
            startActivity(intent);
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new  ChooseAreaFragment()).commit();
        }
    }
}
