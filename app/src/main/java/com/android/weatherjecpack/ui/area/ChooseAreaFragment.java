package com.android.weatherjecpack.ui.area;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.weatherjecpack.adapter.AreaListAdapter;
import com.android.weatherjecpack.databinding.FragmentChooseAreaBinding;
import com.android.weatherjecpack.ui.MainActivity;
import com.android.weatherjecpack.ui.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

public class ChooseAreaFragment extends Fragment {

    private ChooseAreaViewModel viewModel;
    private AreaListAdapter adapter;
    private TextView titleText;
    private ImageButton backButton;
    private ProgressBar loadingBar;
    private RecyclerView recyclerView;
    private LinearLayout layout;

    public ChooseAreaFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentChooseAreaBinding binding =
                FragmentChooseAreaBinding.inflate(inflater,container,false);
        titleText = binding.titleText;
        backButton = binding.backImage;
        loadingBar = binding.loadingProgressBar;
        layout = binding.linear;
        backButton.setOnClickListener(this::onClick);
        initRecyclerView(binding);
        viewModel = new ViewModelProvider(this).get(ChooseAreaViewModel.class);
        viewModel.getProvinces();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observe();
    }

    public void onClick(View v){
        viewModel.onClickBack();
    }

    //初始化RecyclerView
    public void initRecyclerView(FragmentChooseAreaBinding binding){
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AreaListAdapter(new ArrayList<>(),areaListener);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    //RecyclerVIew点击事件监听
    private AreaListAdapter.OnAreaListener areaListener = areaName -> {
        viewModel.isLoading.setValue(true);
        switch (viewModel.currentLevel.getValue()){
            case PROVINCE:
                viewModel.provinceItemClick(areaName);
                break;
            case CITY:
                viewModel.cityItemClick(areaName);
                break;
            case COUNTY:
                viewModel.countyItemClick(areaName);
                break;
            default:break;
        }
    };

    //观察LiveData，更新ui
    private void observe(){
        viewModel.provinces.observe(this, provinces -> {
            if (provinces != null){
                adapter.setList(viewModel.convertProvinces(provinces));
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.cities.observe(this,cities -> {
            if (cities != null){
                adapter.setList(viewModel.convertCities(cities));
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.counties.observe(this,counties -> {
            if (counties != null){
                adapter.setList(viewModel.convertCounties(counties));
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.currentLevel.observe(this,areaLevel -> {
            switch (areaLevel){
                case CITY:
                    titleText.setText(viewModel.selectProvince.getProvinceName());
                    backButton.setVisibility(View.VISIBLE);
                    break;
                case COUNTY:
                    titleText.setText(viewModel.selectCity.getCityName());
                    backButton.setVisibility(View.VISIBLE);
                    break;
                case PROVINCE:
                    titleText.setText("中国");
                    backButton.setVisibility(View.GONE);
                    break;
                default:break;
            }
        });
        viewModel.isSelectFinish.observe(this, aBoolean -> {
            if (aBoolean == true){
                if (getActivity() instanceof MainActivity){
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    intent.putExtra("weather_id",viewModel.selectCounty.getCountryWeatherId());
                    startActivity(intent);
                }else if (getActivity() instanceof WeatherActivity){
                    ((WeatherActivity) getActivity()).drawerLayout.closeDrawers();
                    ((WeatherActivity) getActivity()).viewModel
                            .getWeather(viewModel.selectCounty.getCountryWeatherId());
                }
            }
        });
        viewModel.isLoading.observe(this,aBoolean -> {
            if (aBoolean) showLoadingBar();
            else closeLoadingBar();
        });
    }

    public void showLoadingBar(){
        loadingBar.setVisibility(View.VISIBLE);
    }

    public void closeLoadingBar(){
        loadingBar.setVisibility(View.GONE);
    }

}
