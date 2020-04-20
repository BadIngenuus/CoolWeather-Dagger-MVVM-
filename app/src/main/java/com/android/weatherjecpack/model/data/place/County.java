package com.android.weatherjecpack.model.data.place;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

public class County extends LitePalSupport {

    @SerializedName("name")
    String countryName;

    @SerializedName("id")
    String countryId;

    String cityId;

    @SerializedName("weather_id")
    String countryWeatherId;

    public String getCountryName() {
        return countryName;
    }

    public String getCountryWeatherId() {
        return countryWeatherId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
