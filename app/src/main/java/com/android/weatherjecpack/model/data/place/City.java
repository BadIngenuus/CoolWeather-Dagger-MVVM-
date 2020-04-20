package com.android.weatherjecpack.model.data.place;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import kotlin.jvm.Transient;

public class City extends LitePalSupport {

    @SerializedName("id")
    String cityId;

    @SerializedName("name")
    String cityName;

    String provinceId;

    public String getCityName() {
        return cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
